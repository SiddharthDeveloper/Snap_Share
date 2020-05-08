package com.apkglobal.snapshare

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase

class ChooseUserActivity : AppCompatActivity() {

    var UserlistView: ListView? = null
    var emails: ArrayList<String> = ArrayList()
    var Keys: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_user)

        UserlistView = findViewById(R.id.UserlistView)

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, emails)
        UserlistView?.adapter = adapter


        FirebaseDatabase.getInstance().getReference().child("users").addChildEventListener(object : ChildEventListener {


            override fun onChildAdded(p0: DataSnapshot?, p1: String?) {
                //To change body of created functions use File | Settings | File Templates.
                val email = p0?.child("email")?.value as String
                emails.add(email)
                Keys.add(p0.key)
                adapter.notifyDataSetChanged()


            }


            override fun onCancelled(p0: DatabaseError?) {
                //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildMoved(p0: DataSnapshot?, p1: String?) {
                //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildChanged(p0: DataSnapshot?, p1: String?) {
                //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildRemoved(p0: DataSnapshot?) {
                //To change body of created functions use File | Settings | File Templates.
            }


        })
        // TODO Click on Particular User..
        UserlistView?.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->

            val snapMap: Map<String, String> = mapOf("from" to FirebaseAuth.getInstance().currentUser!!.email!!, "imageName" to intent.getStringExtra("imageName"), "imageURL" to intent.getStringExtra("imageURL"), "message" to intent.getStringExtra("message"))

            FirebaseDatabase.getInstance().getReference().child("users").child(Keys.get(position)).child("snaps").push().setValue(snapMap)

            val intent =Intent(this,SnapsActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)


        }


    }
}
