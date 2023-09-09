package com.ieee.codelink.data.fakeDataProvider

import com.ieee.codelink.R
import com.ieee.codelink.domain.tempModels.TempSearchItem
import com.ieee.codelink.domain.tempModels.TempTeam
import com.ieee.codelink.domain.tempModels.TempUserSearch
import kotlin.random.Random

class FakeDataProvider {

    var fakeTracks: List<TempSearchItem>
    var fakeUsers: List<TempUserSearch>
    var fakeTeams: List<TempTeam>

    init {
        fakeTracks = initFakeTracks()
        fakeUsers = initfakeUsers()
        fakeTeams = initfakeTeams()
    }

    private fun initfakeTeams(): MutableList<TempTeam> {
        val list = mutableListOf<TempTeam>()
        for (i in 0 until 50) {
            var name = "${getFakeTeamFirstName()} ${getFakeTeamLastName()}"
            var teamDescription = getFakeTeamBio()
            var image = getRandomImage()
            val numberOfUsers = Random.nextInt(9) + 1
            var users = initfakeUsers(numberOfUsers)
            list.add(
                TempTeam(
                    name = name,
                    description = teamDescription,
                    image = image,
                    users = users
                )
            )
        }
        return list
    }

    private fun initfakeUsers(num: Int = 26): MutableList<TempUserSearch> {
        val list = mutableListOf<TempUserSearch>()
        for (i in 0 until num) {
            var firstName: String
            var secondName = getRandomManName()
            var img: Int
            var track = getRandomTrack()
            val isMan = Random.nextBoolean()
            firstName = if (isMan) {
                img = getRandomManImage()
                getRandomManName()
            } else {
                img = getRandomGirlImage()
                getRandomGirlName()
            }
            list.add(
                TempUserSearch(
                    name = "$firstName $secondName",
                    image = img,
                    track = track,
                    isFollowed = Random.nextBoolean()
                )
            )
        }
        return list
    }

    private fun getRandomManName(): String {
        val names = arrayOf(
            "Mohamed",
            "David",
            "Samuel",
            "Ahmed",
            "Ella",
            "Michael",
            "Robert",
            "Richard",
            "Tomas",
            "John",
            "Harry",
            "George"
        )
        if (names.isEmpty()) {
            throw IllegalArgumentException("The input array is empty.")
        }
        val randomIndex = Random.nextInt(names.size)
        return names[randomIndex]
    }

    private fun getRandomGirlName(): String {
        val names = arrayOf("Jessica", "Marry", "Olivia", "Layla", "Ella", "Emily", "Amelia")
        if (names.isEmpty()) {
            throw IllegalArgumentException("The input array is empty.")
        }
        val randomIndex = Random.nextInt(names.size)
        return names[randomIndex]
    }

    private fun getFakeTeamFirstName(): Any {
        val names = arrayOf("Galaxy", "Stars", "Egy", "Code", "Skill", "Star", "IEEE", "State")
        if (names.isEmpty()) {
            throw IllegalArgumentException("The input array is empty.")
        }
        val randomIndex = Random.nextInt(names.size)
        return names[randomIndex]
    }

    private fun getFakeTeamLastName(): String {
        val names = arrayOf(
            "Coders",
            "Breakers",
            "Attackers",
            "Titans",
            "Heroes",
            "Stars",
            "Leaders",
            "Founders"
        )
        if (names.isEmpty()) {
            throw IllegalArgumentException("The input array is empty.")
        }
        val randomIndex = Random.nextInt(names.size)
        return names[randomIndex]
    }

    private fun getFakeTeamBio(): String {
        val names = arrayOf(
            "Our team pioneers creative solutions through code.",
            "Crafting clean, efficient code with meticulous care.",
            "A team that excels through teamwork.",
            "Masters of agile methodologies for efficient development.",
            "Turning ideas into digital magic.",
            "Building the digital future, one line at a time.",
            "Charting new territories in software innovation.",
            "Turning vision into tangible results swiftly.",
            "Shaping code for real-world impact.",
            "Weaving data into actionable insights.",
            "Turning complex challenges into elegant solutions.",
            "Engineering the future through code and creativity."
        )
        if (names.isEmpty()) {
            throw IllegalArgumentException("The input array is empty.")
        }
        val randomIndex = Random.nextInt(names.size)
        return names[randomIndex]
    }

    private fun getRandomTrack(): String {
        val names = arrayOf(
            "Android Developer",
            "IOS Developer",
            "UI/UX designer",
            "BackEnd Developer",
            "Engineer",
            "Frontend Developer"
        )
        if (names.isEmpty()) {
            throw IllegalArgumentException("The input array is empty.")
        }
        val randomIndex = Random.nextInt(names.size)
        return names[randomIndex]
    }

    private fun getRandomGirlImage(): Int {
        val images = arrayOf(
            R.drawable.girl1,
            R.drawable.girl2,
            R.drawable.girl3
        )
        if (images.isEmpty()) {
            throw IllegalArgumentException("The input array is empty.")
        }
        val randomIndex = Random.nextInt(images.size)
        return images[randomIndex]
    }

    private fun getRandomManImage(): Int {
        val images = arrayOf(
            R.drawable.man1,
            R.drawable.man2,
            R.drawable.man3
        )
        if (images.isEmpty()) {
            throw IllegalArgumentException("The input array is empty.")
        }
        val randomIndex = Random.nextInt(images.size)
        return images[randomIndex]
    }

    private fun getRandomImage(): Int {
        val images = arrayOf(
            R.drawable.ic_onboarding_1,
            R.drawable.ic_onboarding_2,
            R.drawable.ic_onboarding_3,
            R.drawable.ic_login_image,
            R.drawable.ic_logo,
            R.drawable.robot,
            R.drawable.hacker,
            R.drawable.fix,
            R.drawable.programming,
            R.drawable.teamwork,
        )
        if (images.isEmpty()) {
            throw IllegalArgumentException("The input array is empty.")
        }
        val randomIndex = Random.nextInt(images.size)
        return images[randomIndex]
    }

    private fun initFakeTracks(): MutableList<TempSearchItem> {
        val list = mutableListOf<TempSearchItem>()
        list.add(TempSearchItem("Android", R.drawable.ic_onboarding_1))
        list.add(TempSearchItem("IOS", R.drawable.ic_onboarding_2))
        list.add(TempSearchItem("Front End", R.drawable.ic_onboarding_3))
        list.add(TempSearchItem("Back End", R.drawable.ic_onboarding_1))
        list.add(TempSearchItem("UI/UX", R.drawable.ic_onboarding_3))
        list.add(TempSearchItem("Embedded Systems ", R.drawable.ic_onboarding_2))
        list.add(TempSearchItem("Cyber Security", R.drawable.ic_onboarding_3))
        list.add(TempSearchItem("Flutter", R.drawable.ic_onboarding_1))
        list.add(TempSearchItem("AI", R.drawable.ic_onboarding_2))
        list.add(TempSearchItem("Data Science", R.drawable.ic_onboarding_1))
        return list
    }
}