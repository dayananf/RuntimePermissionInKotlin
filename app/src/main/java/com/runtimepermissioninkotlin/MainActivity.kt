package com.runtimepermissioninkotlin

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    var REQUEST_CODE: Int = 123  // code onCreate permission
    var REQUEST_CODE_CLICK: Int = 124  // code button click permission
    lateinit var btn_permission: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_permission = findViewById<Button>(R.id.btn_permission)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getRuntimePermission()  //  onCreate permission
        }

        //  button click permission
        btn_permission.setOnClickListener(View.OnClickListener {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { // check mobile Version
                var recordpermissionFlag: Int = 0
                var permissionCount: Int = 0

                var recordpermission = ContextCompat.checkSelfPermission(this@MainActivity, android.Manifest.permission.RECORD_AUDIO)

                if (recordpermission != PackageManager.PERMISSION_GRANTED) { // check permission Grant OR Not
                    permissionCount = 1
                    recordpermissionFlag = 1

                    var permisssionArrsy = arrayOfNulls<String>(permissionCount)
                    if (recordpermissionFlag == 1) {
                        permisssionArrsy[0] = android.Manifest.permission.RECORD_AUDIO

                        ActivityCompat.requestPermissions(
                                this@MainActivity,  //context
                                permisssionArrsy,   // permission array[]
                                REQUEST_CODE_CLICK  // Request code  button click permission
                        )
                    }
                } else {
                    Toast.makeText(this, "Already Done", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Normal ", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getRuntimePermission() {

        var readCameraFlag: Int = 0
        var readGalleryFlag: Int = 0
        var permissionCounter: Int = 0
        var permissionCounterNew: Int = 0
        var flag: Int = 0

        // Declare Permission
        var camerapermission = ContextCompat.checkSelfPermission(this@MainActivity, android.Manifest.permission.CAMERA)
        var gallerypermission = ContextCompat.checkSelfPermission(this@MainActivity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if (camerapermission != PackageManager.PERMISSION_GRANTED) {
            readCameraFlag = 1
            permissionCounter += 1
            flag = 1
        }

        if (gallerypermission != PackageManager.PERMISSION_GRANTED) {
            readGalleryFlag = 1
            permissionCounter += 1
            flag = 1
        }

        var permissionArrayList = arrayOfNulls<String>(permissionCounter)
        if (readCameraFlag == 1) {
            permissionArrayList[permissionCounterNew] = android.Manifest.permission.CAMERA
            permissionCounterNew += 1
        }

        if (readGalleryFlag == 1) {
            permissionArrayList[permissionCounterNew] = android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            permissionCounterNew += 1
        }

        if (flag == 1) {
            ActivityCompat.requestPermissions(
                    this@MainActivity,  // Context
                    permissionArrayList,  // ArrayList
                    REQUEST_CODE  // Request code onCreate permission
            )
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Request Done", Toast.LENGTH_SHORT).show()
        }
        if (requestCode == REQUEST_CODE_CLICK && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Click Requset Done", Toast.LENGTH_SHORT).show()
        }
    }
}

