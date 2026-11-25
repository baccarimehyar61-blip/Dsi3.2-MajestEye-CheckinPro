package com.example.checkinpro

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AdminActivity : AppCompatActivity() {

    private var usersList = mutableListOf<User>()
    private lateinit var bdService: BDLocalService
    private lateinit var rvUsers: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        bdService = BDLocalService(this)

        val btnLogout = findViewById<Button>(R.id.btnLogoutAdmin)
        val edtName = findViewById<EditText>(R.id.edtAdminName)
        val edtEmail = findViewById<EditText>(R.id.edtAdminEmail)
        val edtPassword = findViewById<EditText>(R.id.edtAdminPassword)
        val btnAdd = findViewById<Button>(R.id.btnAddUser)
        rvUsers = findViewById(R.id.rvUsers)
        rvUsers.layoutManager = LinearLayoutManager(this)

        btnLogout.setOnClickListener {
            finish()
        }

        refreshList()

        btnAdd.setOnClickListener {
            val name = edtName.text.toString().trim()
            val email = edtEmail.text.toString().trim()
            val pass = edtPassword.text.toString()

            if (name.isEmpty() || email.isEmpty() || pass.length < 6) {
                Toast.makeText(this, "Vérifiez les champs (mdp >= 6)", Toast.LENGTH_SHORT).show()
            } else {
                bdService.addUser(
                    User(
                        fullName = name,
                        email = email,
                        password = pass,
                        role = "TRAVAILLEUR"
                    )
                )
                edtName.text.clear()
                edtEmail.text.clear()
                edtPassword.text.clear()
                refreshList()
                Toast.makeText(this, "Travailleur ajouté", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        refreshList()
    }

    private fun refreshList() {
        usersList = bdService.getAllUsers()
        rvUsers.adapter = UserAdapter(usersList) { selectedUser ->
            if (selectedUser.role == "ADMIN") {
                Toast.makeText(
                    this,
                    "Le compte Admin Système n'est pas modifiable",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val intent = Intent(this, UserDetailActivity::class.java)
                intent.putExtra("user_id", selectedUser.id)
                startActivity(intent)
            }
        }
    }

    class UserAdapter(
        private val list: List<User>,
        private val onItemClick: (User) -> Unit
    ) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

        class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val txtName: TextView = view.findViewById(R.id.txtItemName)
            val txtEmail: TextView = view.findViewById(R.id.txtItemEmail)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_user, parent, false)
            return UserViewHolder(view)
        }

        override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
            val user = list[position]
            holder.txtName.text = "${user.fullName} (${user.role})"
            holder.txtEmail.text = user.email
            holder.itemView.setOnClickListener { onItemClick(user) }
        }

        override fun getItemCount(): Int = list.size
    }
}