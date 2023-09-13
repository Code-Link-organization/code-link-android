package com.ieee.codelink.data.fakeDataProvider

import com.ieee.codelink.R
import com.ieee.codelink.domain.tempModels.TempChatUser
import com.ieee.codelink.domain.tempModels.TempMentor
import com.ieee.codelink.domain.tempModels.TempSearchItem
import com.ieee.codelink.domain.tempModels.TempTeam
import com.ieee.codelink.domain.tempModels.TempUserSearch
import com.ieee.codelink.domain.tempModels.toMentor
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.random.Random

class FakeDataProvider {

    var fakeTracks: MutableList<TempSearchItem>
    var fakeServices: MutableList<TempSearchItem>
    var fakeUsers: MutableList<TempUserSearch>
    var fakeTeams: MutableList<TempTeam>
    var fakeChatsInbox: MutableList<TempChatUser>
    var fakeChatsFriends: MutableList<TempChatUser>
    var fakeChatsCalls: MutableList<TempChatUser>

    init {
        fakeTracks = initFakeTracks()
        fakeUsers = getFakeUsers()
        fakeTeams = getFakeTeams()
        fakeServices = initFakeServices()
        fakeChatsInbox = getFakeChats(Random.nextInt(15)+4)
        fakeChatsCalls = getFakeChats(Random.nextInt(13)+6)
        fakeChatsFriends = getFakeChats(Random.nextInt(16)+5)
    }


    fun getFakeCourses(track : String):List<TempUserSearch>{
        val list = mutableListOf<TempUserSearch>()
        val names = arrayOf(
            "Advanced",
            "Basics",
            "Intermediate",
            "Professional",
            "in 3 hours",
            "Pro",
            "Lvl1",
            "Lvl2",
            "Lvl3",
            "Lvl4",
            "Lvl5",
            "Legacy",
            "In 30 days",
            "From professionals",
            "Start now"
        )
        for (i in 0 until names.size) {
           list.add(
               TempUserSearch(
                   name = "$track ${names[i]}",
                   image = getRandomImage(),
                   track = track
               )
           )
        }
      return list
    }



     fun getFakeTeams(num: Int = 25): MutableList<TempTeam> {
        val list = mutableListOf<TempTeam>()
        for (i in 0 until num) {
            var name = "${getFakeTeamFirstName()} ${getFakeTeamLastName()}"
            var teamDescription = getFakeTeamBio()
            var image = getRandomImage()
            val numberOfUsers = Random.nextInt(9) + 1
            var users = getFakeUsers(numberOfUsers)
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

     fun getFakeUsers(num: Int = 26): MutableList<TempUserSearch> {
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
                    isFollowed = Random.nextBoolean(),
                    isMan = isMan
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
        val names = arrayOf(
            "Galaxy",
            "Stars",
            "Egy",
            "Code",
            "Skill",
            "Star",
            "IEEE",
            "State",
            "Home",
            "Lazy",
            "Universe",
            "Tech",
            "Swift",
            "Bright",
            "Fusion"
        )

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
            "Founders",
            "Owners",
            "Creators",
            "Champions",
            "Engineers",
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

     fun getRandomTrack(): String {
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

     fun getRandomGirlImage(): Int {
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

     fun getRandomManImage(): Int {
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

     fun getRandomManMentorImage(): Int {
        val images = arrayOf(
            R.drawable.man_mentor_1,
            R.drawable.man_mentor_2,
            R.drawable.man_mentor_3
        )
        if (images.isEmpty()) {
            throw IllegalArgumentException("The input array is empty.")
        }
        val randomIndex = Random.nextInt(images.size)
        return images[randomIndex]
    }
     fun getRandomGirlMentorImage(): Int {
        val images = arrayOf(
            R.drawable.woman_mentor_1,
            R.drawable.woman_mentor_2,
            R.drawable.woman_mentor_3
        )
        if (images.isEmpty()) {
            throw IllegalArgumentException("The input array is empty.")
        }
        val randomIndex = Random.nextInt(images.size)
        return images[randomIndex]
    }

     fun getRandomImage(): Int {
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

    private fun initFakeServices(): MutableList<TempSearchItem> {
        val list = ArrayList<TempSearchItem>()
        list.add(TempSearchItem("Users", R.drawable.ic_friends_img))
        list.add(TempSearchItem("Mentor", R.drawable.ic_mentor_img))
        list.add(TempSearchItem("Teams", R.drawable.ic_teams_img))
        list.add(TempSearchItem("Courses", R.drawable.ic_courses_img))
        list.add(TempSearchItem("Communities", R.drawable.ic_connecting_teams))
        list.add(TempSearchItem("Hackathons", R.drawable.ic_team_goal))
        return list
    }

    fun getFakeMentors(): MutableList<TempMentor> {
      val list = getFakeUsers(Random.nextInt(15)+9)
      val mentors = mutableListOf<TempMentor>()
      for (user in list) {
          mentors.add(user.toMentor())
      }
        return mentors
    }

    fun generateRandomTime(): String {
        val hour = Random.nextInt(1, 13) // Random hour between 1 and 12
        val minute = Random.nextInt(0, 60) // Random minute between 0 and 59
        val amPm = if (Random.nextBoolean()) "AM" else "PM" // Random AM or PM

        // Format the time as "h:mm a"
        val timeFormat = SimpleDateFormat("h:mm a")
        val date = Date(0, 0, 0, hour, minute)
        val formattedTime = timeFormat.format(date)

        return formattedTime
    }
    fun getFakeChats(num: Int = 12):MutableList<TempChatUser>{
        val users = getFakeUsers(12)
        val chats = mutableListOf<TempChatUser>()
        for (user in users) {
            chats.add(TempChatUser(
                name = user.name,
                image = user.image,
                time = generateRandomTime(),
                lastMessage = getFakeTeamBio()
            ))
        }
        return chats
    }

}