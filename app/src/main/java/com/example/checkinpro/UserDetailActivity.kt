package com.example.checkinpro

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class UserDetailActivity : AppCompatActivity() {

    private lateinit var bdService: BDLocalService
    private var currentUser: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        bdService = BDLocalService(this)
        val userId = intent.getIntExtra("user_id", -1)

        val edtName = findViewById<EditText>(R.id.edtDetailName)
        val edtEmail = findViewById<EditText>(R.id.edtDetailEmail)
        val rgRole = findViewById<RadioGroup>(R.id.rgRole)
        val rbTravailleur = findViewById<RadioButton>(R.id.rbTravailleur)
        val rbAdminRh = findViewById<RadioButton>(R.id.rbAdminRh)
        val rbResponsableEquipe = findViewById<RadioButton>(R.id.rbResponsableEquipe)
        val btnUpdate = findViewById<Button>(R.id.btnUpdateUser)
        val btnDelete = findViewById<Button>(R.id.btnDeleteUser)

        currentUser = bdService.getUserById(userId)
        currentUser?.let {
            edtName.setText(it.fullName)
            edtEmail.setText(it.email)

            when (it.role) {
                "ADMINISTRATEUR_RH" -> rbAdminRh.isChecked = true
                "RESPONSABLE_EQUIPE" -> rbResponsableEquipe.isChecked = true
                else -> rbTravailleur.isChecked = true
            }
        }

        // حماية حساب الـ ADMIN من التعديل أو الحذف
        if (currentUser?.role == "ADMIN") {
            btnUpdate.isEnabled = false
            btnDelete.isEnabled = false
        }

        btnUpdate.setOnClickListener {
            currentUser?.let { old ->
                val selectedRole = when (rgRole.checkedRadioButtonId) {
                    R.id.rbAdminRh -> "ADMINISTRATEUR_RH"
                    R.id.rbResponsableEquipe -> "RESPONSABLE_EQUIPE"
                    else -> "TRAVAILLEUR"
                }

                val updated = old.copy(
                    fullName = edtName.text.toString().trim(),
                    email = edtEmail.text.toString().trim(),
                    role = selectedRole
                )
                bdService.updateUser(updated)
                Toast.makeText(this, "Modifié avec succès", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        btnDelete.setOnClickListener {
            currentUser?.id?.let { id ->
                bdService.deleteUser(id)
                Toast.makeText(this, "Supprimé avec succès", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}