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

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.android.navigation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("UNUSED_VARIABLE")
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        drawerLayout = binding.drawerLayoutMain

        val navFragmentController = this.findNavController(R.id.nav_host_fragment_main)
        NavigationUI.setupActionBarWithNavController(this, navFragmentController, drawerLayout)
        NavigationUI.setupWithNavController(binding.navViewMain, navFragmentController)


        appBarConfiguration = AppBarConfiguration(navFragmentController.graph, drawerLayout)

        navFragmentController.addOnDestinationChangedListener { nc: NavController, nd: NavDestination, args: Bundle? ->
            if (nd.id == nc.graph.startDestination) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                drawerLayout.closeDrawer(binding.navViewMain)
            } else {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
        }


    }


    override fun onSupportNavigateUp(): Boolean {
        val navFragmentController = this.findNavController(R.id.nav_host_fragment_main)

//        return NavigationUI.navigateUp(navFragmentController, drawerLayout)
        return navFragmentController.navigateUp()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (drawerLayout.isDrawerOpen(binding.navViewMain)) {
            drawerLayout.closeDrawer(binding.navViewMain)
        }
        if (!drawerLayout.isDrawerOpen(binding.navViewMain) && item.itemId != R.id.about_menu_item){
            drawerLayout.openDrawer(binding.navViewMain)
        }

        return super.onOptionsItemSelected(item)
    }



}
