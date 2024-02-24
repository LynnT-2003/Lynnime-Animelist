import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.lynnime.R
import com.example.lynnime.utils.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class CarouselFragment : Fragment() {

    private lateinit var viewPager: ViewPager2
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_carousel, container, false)
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        viewPager = view.findViewById(R.id.slideViewPager)
//        viewPagerAdapter = ViewPagerAdapter(requireContext())
//        viewPager.adapter = viewPagerAdapter
//
//        val tabIndicator = view.findViewById<TabLayout>(R.id.tabIndicator)
//        TabLayoutMediator(tabIndicator, viewPager) { tab, position ->
//            // Here we can set text over the tabs as indicators if needed.
//        }.attach()
//
//        // Optionally, if you have buttons for next and back, set their click listeners.
//        val nextButton = view.findViewById<Button>(R.id.nextbtn)
//        nextButton.setOnClickListener {
//            viewPager.currentItem += 1
//        }
//
//        val backButton = view.findViewById<Button>(R.id.backbtn)
//        backButton.setOnClickListener {
//            viewPager.currentItem -= 1
//        }
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager = view.findViewById(R.id.slideViewPager)
        viewPagerAdapter = ViewPagerAdapter(requireContext())
        viewPager.adapter = viewPagerAdapter

        val tabIndicator = view.findViewById<TabLayout>(R.id.tabIndicator)
        TabLayoutMediator(tabIndicator, viewPager) { tab, position ->
            // Here we can set text over the tabs as indicators if needed.
        }.attach()

        val nextButton = view.findViewById<Button>(R.id.nextbtn)
        val backButton = view.findViewById<Button>(R.id.backbtn)
        val skipButton = view.findViewById<Button>(R.id.skipButton)

        skipButton.setOnClickListener {
            navigateToHome()
        }

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if (position == viewPagerAdapter.itemCount - 1) {
                    // Last page
                    nextButton.text = "Finish"
                    nextButton.setOnClickListener {
                        // Navigate to HomeFragment when "Finish" is clicked
                        navigateToHome()
                    }
                } else {
                    // Not last page
                    nextButton.text = "Next" // Change text back to "Next" or show if previously hidden
                    nextButton.setOnClickListener {
                        viewPager.currentItem += 1
                    }
                    backButton.visibility = if (position == 0) View.INVISIBLE else View.VISIBLE
                }
            }
        })

        backButton.setOnClickListener {
            viewPager.currentItem -= 1
        }
    }


    // Optionally, implement a method to navigate to the HomeFragment after the carousel ends
    private fun navigateToHome() {
        findNavController().navigate(R.id.action_carouselFragment_to_signInFragment)
    }

}
