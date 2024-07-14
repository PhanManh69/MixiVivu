package com.emanh.mixivivu.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.emanh.mixivivu.model.ShipModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ShipViewModel : ViewModel() {
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    private val _ship = MutableLiveData<MutableList<ShipModel>>()

    val ship: LiveData<MutableList<ShipModel>> = _ship

    fun loadShip() {
        val ref = firebaseDatabase.getReference("Ship")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<ShipModel>()
                for (childSnapshot in snapshot.children) {
                    val ship = childSnapshot.getValue(ShipModel::class.java)
                    if (ship != null) {
                        lists.add(ship)
                    }
                    _ship.value = lists
                }
                _ship.value = lists
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}