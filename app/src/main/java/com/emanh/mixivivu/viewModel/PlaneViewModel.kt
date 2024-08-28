package com.emanh.mixivivu.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.emanh.mixivivu.model.PlaneModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PlaneViewModel : ViewModel() {
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    private val _plane = MutableLiveData<MutableList<PlaneModel>>()
    val plane: LiveData<MutableList<PlaneModel>> get() = _plane

    fun loadPlane(inputStartingPoint: String, inputDestination: String) {
        val ref = firebaseDatabase.getReference("plane")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<PlaneModel>()
                for (childSnapshot in snapshot.children) {
                    val plane = childSnapshot.getValue(PlaneModel::class.java)
                    plane?.let {
                        if (it.startingPoint.contains(inputStartingPoint, ignoreCase = true) &&
                            it.destination.contains(inputDestination, ignoreCase = true)) {
                            lists.add(it)
                        }
                    }
                }
                _plane.value = lists
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("PlaneViewModel", "Lỗi khi lấy danh sách plane: ${error.message}")
            }

        })
    }
}