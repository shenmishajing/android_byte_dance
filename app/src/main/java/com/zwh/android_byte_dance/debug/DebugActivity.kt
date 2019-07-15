package com.zwh.android_byte_dance.debug

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.zwh.android_byte_dance.R
import kotlinx.android.synthetic.main.activity_debug.*
import java.io.File
import java.io.IOException
import java.util.LinkedHashMap

class DebugActivity : AppCompatActivity() {
    companion object {
        private val REQUEST_CODE_STORAGE_PERMISSION = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_debug)
        setTitle(R.string.action_debug)

        btn_print_path.setOnClickListener(View.OnClickListener {
            val sb = StringBuilder()
            sb.append("===== Internal Private =====\n").append(getInternalPath())
                .append("===== External Private =====\n").append(getExternalPrivatePath())
                .append("===== External Public =====\n").append(getExternalPublicPath())
            text_path.text = sb
        })

        btn_request_permission.setOnClickListener(View.OnClickListener {
            val state = ActivityCompat.checkSelfPermission(
                this@DebugActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            if (state == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(
                    this@DebugActivity, "already granted",
                    Toast.LENGTH_SHORT
                ).show()
                return@OnClickListener
            }
            ActivityCompat.requestPermissions(
                this@DebugActivity,
                arrayOf<String>(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_CODE_STORAGE_PERMISSION
            )
        })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (permissions.isEmpty() || grantResults.isEmpty()) {
            return
        }
        if (requestCode == REQUEST_CODE_STORAGE_PERMISSION) {
            val state = grantResults[0]
            if (state == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(
                    this@DebugActivity, "permission granted",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (state == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(
                    this@DebugActivity, "permission denied",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun getInternalPath(): String {
        val dirMap = LinkedHashMap<String, File>()
        dirMap["cacheDir"] = cacheDir
        dirMap["filesDir"] = filesDir
        dirMap["customDir"] = getDir("custom", MODE_PRIVATE)
        return getCanonicalPath(dirMap)
    }

    private fun getExternalPrivatePath(): String {
        val dirMap = LinkedHashMap<String, File>()
        dirMap["cacheDir"] = externalCacheDir!!
        dirMap["filesDir"] = getExternalFilesDir(null)!!
        dirMap["picturesDir"] = getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return getCanonicalPath(dirMap)
    }

    private fun getExternalPublicPath(): String {
        val dirMap = LinkedHashMap<String, File>()
        dirMap["rootDir"] = Environment.getExternalStorageDirectory()
        dirMap["picturesDir"] = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        return getCanonicalPath(dirMap)
    }

    private fun getCanonicalPath(dirMap: Map<String, File>): String {
        val sb = StringBuilder()
        try {
            for (name in dirMap.keys) {
                sb.append(name)
                    .append(": ")
                    .append((dirMap[name] ?: error("")).canonicalPath)
                    .append('\n')
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return sb.toString()
    }
}
