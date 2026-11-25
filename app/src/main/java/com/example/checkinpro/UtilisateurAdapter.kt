
package com.example.checkinpro

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UtilisateurAdapter(
    private val utilisateurs: List<User>,
    private val onItemClick: (User) -> Unit
) : RecyclerView.Adapter<UtilisateurAdapter.UtilisateurViewHolder>() {

    class UtilisateurViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {

        val nom: TextView = view.findViewById(R.id.textNom)
        val prenom: TextView = view.findViewById(R.id.textPrenom)
        val email: TextView = view.findViewById(R.id.textEmail)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UtilisateurViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_utilisateur, parent, false)

        return UtilisateurViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: UtilisateurViewHolder,
        position: Int
    ) {

        val utilisateur = utilisateurs[position]

        holder.nom.text = "Nom : ${utilisateur.nom}"
        holder.prenom.text = "Prénom : ${utilisateur.prenom}"
        holder.email.text = "Email : ${utilisateur.email}"

        holder.itemView.setOnClickListener {
            onItemClick(utilisateur)
        }
    }

    override fun getItemCount(): Int = utilisateurs.size
}
