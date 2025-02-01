package tg.ulcrsandroid.carspooling

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.graphics.Color
import android.icu.util.Calendar
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import tg.ulcrsandroid.carspooling.core.models.RideModel
import tg.ulcrsandroid.carspooling.core.utils.GlobalUser
import tg.ulcrsandroid.carspooling.databinding.FragmentDemandesTrajetsBinding
import tg.ulcrsandroid.carspooling.databinding.FragmentCreerTrajetBinding
import tg.ulcrsandroid.carspooling.features.ridemanagement.viewModel.RideMgmtViewModel


val lieux = listOf(
    "Paris",
    "Marseille",
    "Lyon",
    "Nice",
    "Strasbourg",
    "Lille",
    "Toulouse",
    "Bordeaux",
    "Nantes",
    "Montpellier"
)
class CreerTrajetFragment : Fragment() {
    private var _ui: FragmentCreerTrajetBinding? = null
    private lateinit var rideMgmtViewModel: RideMgmtViewModel
    private val ui get() = _ui!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _ui = FragmentCreerTrajetBinding.inflate(inflater, container, false)
        val application = requireActivity().application as CarSpoolingApplication
        rideMgmtViewModel = ViewModelProvider(
            this,
            application.factoryRideManagement
        )[RideMgmtViewModel::class.java]

        rideMgmtViewModel.toastMessage.observe(viewLifecycleOwner) { message ->
            message?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                rideMgmtViewModel.clearToastMessage()
            }
        }

        return ui.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurer la Toolbar
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.setTitleTextAppearance(requireContext(), R.style.CustomToolbarStyle)
        toolbar.title = "Creer un trajet"

        val adapterDepart = ArrayAdapter(requireContext(), R.layout.item_suggestion, lieux)
        ui.departInput.setAdapter(adapterDepart)
        ui.departInput.threshold = 1
        ui.departInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Filtre les suggestions en fonction de l'entrée
                val filteredSuggestions = lieux.filter { it.startsWith(s.toString(), ignoreCase = true) }
                val filteredAdapter = ArrayAdapter(requireContext(), R.layout.item_suggestion, filteredSuggestions)
                ui.departInput.setAdapter(filteredAdapter)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        val adapterDest = ArrayAdapter(requireContext(), R.layout.item_suggestion, lieux)
        ui.destinationInput.setAdapter(adapterDest)
        ui.destinationInput.threshold = 1
        ui.destinationInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Filtre les suggestions en fonction de l'entrée
                val filteredSuggestions = lieux.filter { it.startsWith(s.toString(), ignoreCase = true) }
                val filteredAdapter = ArrayAdapter(requireContext(), R.layout.item_suggestion, filteredSuggestions)
                ui.destinationInput.setAdapter(filteredAdapter)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        ui.dateEditText.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(requireContext(), R.style.CustomDialogStyle, { _, selectedYear, selectedMonth, selectedDay ->
                val date = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                ui.dateEditText.setText(date) // Affiche la date sélectionnée dans l'EditText
            }, year, month, day)

            datePicker.show()
        }

        ui.timeEditText.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val timePicker = TimePickerDialog(requireContext(), R.style.CustomDialogStyle               , { _, selectedHour, selectedMinute ->
                val time = String.format("%02d:%02d", selectedHour, selectedMinute)
                ui.timeEditText.setText(time) // Affiche l'heure sélectionnée dans l'EditText
            }, hour, minute, true)

            timePicker.show()
        }

        val options = listOf("Nombre de places", "1", "2", "3", "4", "5")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(R.layout.item_spinner)
        ui.numberSpinner.adapter = adapter

        // Empêcher la sélection de l'option "hint" (première option)
        ui.numberSpinner.setSelection(0, false)

        ui.numberSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if (position == 0) {
                    (parent.getChildAt(0) as? TextView)?.setTextColor(Color.GRAY)
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        ui.creerButton.setOnClickListener {
            val lieuDepart = ui.departInput.text
            val destination = ui.destinationInput.text
            val date = ui.dateEditText.text
            val heure = ui.timeEditText.text
            val prix = ui.priceEditText.text
            val placesDisponibles = ui.numberSpinner.selectedItem.toString()

            val rideModel = GlobalUser.user?.let { it1 ->
                RideModel(
                    driverId = it1.uid,
                    departure = lieuDepart.toString(),
                    arrival = destination.toString(),
                    dateRide = date.toString(),
                    hoursRide = heure.toString(),
                    price = prix.toString().toDouble(),
                    placeNumber = placesDisponibles.toInt()
                )
            }
            if (rideModel != null) {
                rideMgmtViewModel.addRide(rideModel)
            }
            Toast.makeText(requireContext(), "Trajet ajouté", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _ui = null
    }
}