package tg.ulcrsandroid.carpooling

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import tg.ulcrsandroid.carpooling.databinding.FragmentChatBinding
import tg.ulcrsandroid.carpooling.databinding.FragmentDemandesTrajetsBinding

class ChatFragment : Fragment() {

    private var _ui: FragmentChatBinding? = null
    private val ui get() = _ui!!
    private val messages = mutableListOf<Message>()
    private lateinit var adapter: ChatAdapter
    private val currentUserId = "123"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _ui = FragmentChatBinding.inflate(inflater, container, false)
        return ui.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomNav = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNav?.visibility = View.GONE

        // Configurer la Toolbar
        val toolbar = view.findViewById<Toolbar>(R.id.chatToolbar)
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.setTitleTextAppearance(requireContext(), R.style.CustomToolbarStyle)
        (toolbar.layoutParams as ViewGroup.MarginLayoutParams).setMargins(0, 0, 0, 0)
        toolbar.setContentInsetsAbsolute(0, 0)
        toolbar.setContentInsetsRelative(0, 0)
        toolbar.title = ""

        adapter = ChatAdapter(messages, currentUserId)
        ui.recyclerViewMessages.layoutManager = LinearLayoutManager(context)
        ui.recyclerViewMessages.adapter = adapter

        ui.iconChat.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        loadMessages()

//        ui.buttonSend.setOnClickListener {
//            val text = ui.editTextMessage.text.toString().trim()
//            if (text.isNotEmpty()) {
//                val message = Message(
//                    senderId = currentUserId,
//                    receiverId = receiverId,
//                    message = text
//                )
//                chatRef.add(message)
//                editTextMessage.text.clear()
//            }
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val bottomNav = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNav?.visibility = View.VISIBLE
        _ui = null
    }

    private fun loadMessages() {
        // Simuler des messages (à remplacer par des données de Firebase ou Realm)
        messages.add(Message(senderId = "123", receiverId = "456", message = "Salut !"))
        messages.add(Message(senderId = "456", receiverId = "123", message = "Coucou, ça va ?"))
        messages.add(Message(senderId = "123", receiverId = "456", message = "Oui merci !"))

        adapter.notifyDataSetChanged()
    }

}