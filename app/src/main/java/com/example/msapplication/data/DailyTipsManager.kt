package com.example.msapplication.data

import kotlin.random.Random

object DailyTipsManager {
    private val dailyTips = arrayOf(
        "Tip of the day: Remember to stay hydrated and maintain a balanced diet.",
        "Tip of the day: Incorporate regular exercise into your routine for better well-being.",
        "Tip of the day: Get enough sleep to support your overall health.",
        "Tip of the day: Practice mindfulness or meditation for stress relief.",
        "Tip of the day: Connect with loved ones to strengthen your support system.",
        "Tip of the day: Take breaks to stretch and move throughout the day.",
        "Tip of the day: Prioritize self-care and engage in activities you enjoy.",
        "Tip of the day: Follow your treatment plan and attend medical appointments.",
        "Tip of the day: Stay informed about MS research and new treatments.",
        "Tip of the day: Keep a positive mindset and celebrate small victories.",
        "Tip of the day: Practice deep breathing exercises for relaxation.",
        "Tip of the day: Include colorful fruits and vegetables in your meals.",
        "Tip of the day: Set realistic goals and celebrate your achievements.",
        "Tip of the day: Keep a journal to track your thoughts and emotions.",
        "Tip of the day: Engage in hobbies that bring you joy and fulfillment.",
        "Tip of the day: Connect with nature by spending time outdoors.",
        "Tip of the day: Learn to delegate tasks and ask for help when needed.",
        "Tip of the day: Stay organized to reduce stress and improve efficiency.",
        "Tip of the day: Educate friends and family about MS to foster understanding.",
        "Tip of the day: Practice gratitude to focus on the positive aspects of life.",
        "Tip of the day: Attend support groups to connect with others facing similar challenges.",
        "Tip of the day: Stay informed about adaptive tools and technologies that can enhance daily living with MS.",
        "Tip of the day: Prioritize rest and relaxation to recharge your energy."
        // Add more tips as needed
    )

    fun getDailyTip(): String {
        val index = Random.nextInt(dailyTips.size)
        return dailyTips[index]
    }
}

