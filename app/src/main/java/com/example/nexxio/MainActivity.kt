package com.example.nexxio

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import com.example.nexxio.adapter.BaseAdapter
import com.example.nexxio.adapter.setClickListener
import com.example.nexxio.data.NexxioData
import com.example.nexxio.data.ResultModel
import com.example.nexxio.databinding.ActivityMainBinding
import com.example.nexxio.viewholder.OnItemClickListener
import com.google.gson.Gson
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity(), OnItemClickListener, setClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: BaseAdapter
    var nexxioData: NexxioData? = null
    var pos: Int? = null
    private var commentText: String = ""
    private var commentId: String = ""
    private var picId: String = ""

    val launcher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback<ActivityResult> {
            if (it.resultCode == Activity.RESULT_OK) {
                val photo = it.data?.extras?.get("data") as? Bitmap
                if (photo != null) {
                    val tempFile = File.createTempFile("temprentpk", ".png")
                    val bytes = ByteArrayOutputStream()
                    photo.compress(Bitmap.CompressFormat.PNG, 100, bytes)
                    val bitmapData = bytes.toByteArray()

                    val fileOutPut = FileOutputStream(tempFile)
                    fileOutPut.write(bitmapData)
                    fileOutPut.flush()
                    fileOutPut.close()
                    Uri.fromFile(tempFile)
                    nexxioData?.imagePath = Uri.fromFile(tempFile)
                    nexxioData?.let { it1 -> pos?.let { it2 -> adapter.updateItem(it1, it2) } }
                    resultElement.add(ResultModel(picId, nexxioData?.imagePath.toString() ?: " "))
                }
            }
        })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val inputString = loadJSONFromAssets("nexxio.json")
        val gson = Gson()
        val post: List<NexxioData> =
            gson.fromJson(inputString, Array<NexxioData>::class.java).toList()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        adapter = BaseAdapter().apply {
            setItems(post)
            setOnItemClickListener(this@MainActivity)
        }
        binding.recyclerviewMain.adapter = adapter
        binding.enlargedImage.visibility = View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.Submit) {
            Toast.makeText(this, "Submit Clicked", Toast.LENGTH_SHORT).show()

            resultElement.groupBy { it.id }.forEach {
                Log.d("nexio", it.key + " " + it.value.maxOf { it.value })
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemClick(data: NexxioData, position: Int) {
        if (data.imagePath != null) {
            data.imagePath = null
            adapter.updateItem(data, position)

        } else {
            nexxioData = data
            pos = position
            picId = data.id
            launcher.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
        }
    }

    override fun getId(data: String, position: Int) {
        nexxioData?.id = data
    }

    private var resultElement: MutableList<ResultModel> = mutableListOf()
    override fun getChoice(id: String, data: String, position: Int) {
        resultElement.add(ResultModel(id, data))
    }

    override fun getComment(id: String, data: String, position: Int) {
        commentId = id
        resultElement.add(ResultModel(id, data))
    }

    override fun onImageClick(data: NexxioData, position: Int) {
        binding.enlargedImage.visibility = View.VISIBLE
        binding.enlargedImage.setImageURI(data.imagePath)
        binding.enlargedImage.adjustViewBounds = true
    }

    fun onClickEnlargedImage(view: View) {
        binding.enlargedImage.visibility = View.GONE
    }
}

fun Context.loadJSONFromAssets(fileName: String): String {
    return assets.open(fileName).bufferedReader().use { it.readText() }
}