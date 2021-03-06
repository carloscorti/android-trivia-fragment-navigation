/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.navigation

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.android.navigation.databinding.FragmentGameWonBinding


class GameWonFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val binding: FragmentGameWonBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_game_won, container, false)

        setHasOptionsMenu(true)

        binding.nextMatchButton.setOnClickListener { view : View ->
            view.findNavController().navigate(GameWonFragmentDirections.actionGameWonFragmentToGameFragment())
        }

        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.congratulations)

        val args = GameWonFragmentArgs.fromBundle(arguments!!)

//        Toast.makeText(context, "${args.numQuestions} ${args.questionIndex}", Toast.LENGTH_SHORT).show()

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.winner_menu, menu)
        // check if the intent in activity this fragment is from resolves in any other app which can handle
        // the implicit itent (the action the intent emits action)
        if(null == getShareIntent().resolveActivity(requireActivity().packageManager)) {
            // if no ap can handle implicit intent action hide share item from menu
            menu.findItem(R.id.share).isVisible = false
        }
    }

    private fun getShareIntent(): Intent {
        val args = GameWonFragmentArgs.fromBundle(arguments!!)
//        val shareIntent = Intent(Intent.ACTION_SEND)
//        shareIntent.type = "text/plain"
//        shareIntent.putExtra(Intent.EXTRA_TEXT,
//                getString(R.string.share_success_text, args.questionIndex, args.numQuestions))
//        return shareIntent
        return ShareCompat.IntentBuilder.from(requireActivity())
                .setType("text/plain")
                .setText(getString(R.string.share_success_text, args.questionIndex, args.numQuestions))
                .intent
    }

    private fun shareSuccess() {
        startActivity(getShareIntent())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.share) shareSuccess()
        return super.onOptionsItemSelected(item)
    }

}
