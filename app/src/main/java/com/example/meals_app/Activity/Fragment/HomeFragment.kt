package com.example.meals_app.Activity.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.meals_app.Activity.CategoryActivity
import com.example.meals_app.Activity.LoginActivity
import com.example.meals_app.Activity.MealActivity
import com.example.meals_app.Adapter.Category.Category_Adapter
import com.example.meals_app.Adapter.CountaryAdapter
import com.example.meals_app.CountaryMealsActivity
import com.example.meals_app.Data.Meal
import com.example.meals_app.R
import com.example.meals_app.databinding.FragmentHomeBinding
import com.example.meals_app.viewModel.HomeViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeMvvm: HomeViewModel
    private lateinit var categoryAdapter: Category_Adapter
    private lateinit var countaryAdapter: CountaryAdapter
    private lateinit var loadinCurrent: ProgressBar
    private lateinit var username :TextView
    private lateinit var logoutbtn :Button
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeMvvm = ViewModelProvider(this)[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadinCurrent = view.findViewById(R.id.countary_bar_pro)
        homeMvvm = ViewModelProvider(this).get(HomeViewModel::class.java)
        username = view.findViewById(R.id.userName)


        //firebase
        logoutbtn = view.findViewById(R.id.logout_btn)
         mAuth = FirebaseAuth.getInstance()
         val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
         mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
         logoutbtn.setOnClickListener {
            signOutAndStartSignInActivity()
        }
         val auth = Firebase.auth
         val user = auth.currentUser!!
         username.text = user.displayName.toString()           // display name


        // Initialize and observe the random meal
        homeMvvm.getRandomMeal()
        setupRandomMealObserver()
        setupRandomMealClickListener()

        // Initialize and observe categories
        setupCategoryRecyclerView()
        homeMvvm.getAllCategory()
        setupCategoryObserver()
        setupCategoryClickListener()

        // Initialize the country RecyclerView
        setupCountryRecyclerView()
        homeMvvm.fetchCountryList()
        observeCountryData()
        setupCountryClickListener()
    }


   /* private fun putImage(){
        val googleSignInAccount = GoogleSignIn.getLastSignedInAccount(requireContext())
        if (googleSignInAccount != null) {
            val photoUrl = googleSignInAccount.photoUrl
            Glide.with(this)
                .load(photoUrl)
                .into(userImage.findViewById(R.id.userImage))
        }
    }*/


   private fun signOutAndStartSignInActivity() {
       // Sign out from Firebase
       mAuth.signOut()

       // Sign out from Google
       mGoogleSignInClient.signOut().addOnCompleteListener(requireActivity()) { task ->
           if (task.isSuccessful) {
               // Start StartActivity and finish current activity
               val intent = Intent(requireActivity(), LoginActivity::class.java)
               startActivity(intent)
               requireActivity().finish()
           } else {
               // Handle failure
               // You might want to display a message or handle the error
           }
       }
   }

    private fun setupCountryClickListener() {
        countaryAdapter.onItemClickListener = { country ->
            val intent = Intent(activity, CountaryMealsActivity::class.java).apply {
                putExtra("COUNTRY_NAME", country.strArea)
            }
            startActivity(intent)
        }
    }

    private fun setupCountryRecyclerView() {
        countaryAdapter = CountaryAdapter(listOf()) // Pass the context here

        binding.countaryRv.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = countaryAdapter
        }
    }


    private fun observeCountryData() {
        homeMvvm._countryList.observe(viewLifecycleOwner, Observer { countryList ->
            countryList?.let {
                countaryAdapter.setCountaryList(it.meals)  //
                loadinCurrent.visibility = View.GONE
            }
        })
    }


    private fun setupCategoryClickListener() {
        categoryAdapter.onClickedItem = { cat ->
            val intent = Intent(activity, CategoryActivity::class.java).apply {
                putExtra("Category_name",cat.strCategory)
            }
            startActivity(intent)
        }
    }

    private fun setupCategoryRecyclerView() {
        binding.categoryProgressBar.visibility = View.VISIBLE

        binding.recycleViewCategory.apply {
            categoryAdapter = Category_Adapter(listOf()) // Initialize Adapter
            layoutManager = GridLayoutManager(context, 3)
            adapter = categoryAdapter

        }
    }

    private fun setupCategoryObserver() {
        homeMvvm.categoryLiveData.observe(viewLifecycleOwner, Observer { categories ->
            categoryAdapter.setCategotyList(categories)  // send data to adapter
            binding.categoryProgressBar.visibility = View.GONE
        })
    }

    private fun setupRandomMealClickListener() {
        binding.randomMealCard.setOnClickListener {
            homeMvvm.randomMealLiveData.value?.let {meal ->
                val intent = Intent(context, MealActivity::class.java).apply {
                    putExtra("meal_id", meal.idMeal)
                    putExtra("meal_name", meal.strMeal)
                    putExtra("meal_image", meal.strMealThumb)
                    putExtra("meal_instructions", meal.strInstructions)
                    putExtra("category", meal.strCategory)
                    putExtra("Area", meal.strArea)
                    putExtra("meal_ingredients", getIngredientsList(meal))
                    putExtra("youtube", meal.strYoutube)
                }
                startActivity(intent)
            }
        }
    }

    private fun getIngredientsList(meal: Meal): ArrayList<String> {
        val ingredients = ArrayList<String>()
        meal.strIngredient1?.let { ingredients.add(it) }
        meal.strIngredient2?.let { ingredients.add(it) }
        meal.strIngredient3?.let { ingredients.add(it) }
        meal.strIngredient4?.let { ingredients.add(it) }
        meal.strIngredient5?.let { ingredients.add(it) }
        meal.strIngredient6?.let { ingredients.add(it) }
        return ingredients
    }

    private fun setupRandomMealObserver() {
        homeMvvm.randomMealLiveData.observe(viewLifecycleOwner, Observer { meal ->
            meal?.let {
                binding.randomMealTxt.text = it.strMeal
                Glide.with(this)
                    .load(it.strMealThumb)
                    .into(binding.randomMealImg) // Ensure you have an ImageView with this ID
            }
            binding.progressBar.visibility = View.GONE
        })
    }
}
