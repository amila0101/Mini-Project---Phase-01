package com.sm.travelapp.data

import com.sm.travelapp.R

object ProductRepository {
    val products = listOf(
        Product(
            id = 1,
            name = "Indonesia Tour",
            description ="Discover the wonders of Indonesia, a country of over 17,000 islands, each offering its own unique charm. From the serene beaches of Bali to the volcanic landscapes of Java, experience a diverse range of natural beauty. This app guides you through the best cultural hotspots, including ancient temples like Borobudur, and helps you connect with local customs and traditions. Explore lush rainforests, stunning coral reefs, and world-famous surf spots. Whether you’re seeking adventure in the wild or relaxation in luxury resorts, Indonesia has it all. Navigate through various cities with local recommendations for food, accommodations, and attractions. The app also includes practical travel tips, transportation guides, and must-see itineraries to help you plan the perfect trip.",
            price = 1499.99,
            imageRes = R.drawable.bali_indo,
            location = "Bali, Indonesia",
            duration = "7 days"
        ),
        Product(
            id = 2,
            name = "Paris City Tour",
            description = "Explore the enchanting streets of Paris, a city synonymous with romance, art, and history. This app offers curated walking tours that lead you through the heart of the city, from the Eiffel Tower to the Louvre Museum. Learn about Paris’s architectural marvels like Notre Dame and Sacré-Cœur Basilica while discovering hidden gems in the Latin Quarter. Enjoy French cuisine with recommendations for the best cafés, patisseries, and Michelin-starred restaurants. Take a stroll along the Seine River or spend an afternoon in the gardens of Versailles. Whether you’re an art lover, a foodie, or a history buff, Paris City Tour provides insider knowledge to make the most of your visit. This app also includes maps, metro guides, and tips on navigating the city like a local.",
            price = 1999.99,
            imageRes = R.drawable.paris_france,
            location = "Paris, France",
            duration = "5 days"
        ),
        Product(
            id = 3,
            name = "Tokyo Adventure",
            description = "Step into the future and the past with the Tokyo Adventure app, a guide to one of the world’s most vibrant and technologically advanced cities. From the neon lights of Shibuya Crossing to the historic temples of Asakusa, Tokyo is a city of contrasts. Visit high-tech districts like Akihabara for the latest in electronics and anime culture, or immerse yourself in the tranquility of traditional Japanese gardens. The app features guides to Tokyo’s best shopping, including world-famous fashion districts like Harajuku. Find the best ramen shops, sushi spots, and izakayas to experience authentic Japanese cuisine. Tokyo Adventure also highlights seasonal events like cherry blossom festivals and offers itineraries for exploring surrounding areas such as Mount Fuji and Yokohama. With detailed maps and tips on public transport, this app is your ultimate companion for navigating Tokyo.",
            price =2399.99,
            imageRes = R.drawable.tokiyo_adve,
            location = "Tokyo, Japan",
            duration = "6 days"
        ),
        Product(
            id = 4,
            name = "santorini Getaway",
            description = "Escape to the breathtaking island of Santorini, known for its white-washed buildings, blue-domed churches, and stunning views of the Aegean Sea. This app is your guide to the perfect Greek island vacation, offering itineraries that highlight Santorini’s famous sunsets and beautiful beaches. Discover the charm of small villages like Oia and Fira, where you can wander cobblestone streets lined with boutique shops and local restaurants. The app provides recommendations for luxury hotels, hidden vineyards, and seaside tavernas where you can taste the finest Greek wines and cuisine. Whether you’re seeking a romantic getaway or an adventure with friends, Santorini Getaway offers activities like boat tours, hiking trails, and historical tours of ancient ruins. Plan your dream vacation with insider tips on the best time to visit and how to navigate the island.",
            price = 1799.99,
            imageRes = R.drawable.santorini,
            location = "santorini, Greece",
            duration = "5 days"
        ),
        Product(
            id = 9,
            name = "Dubai Luxury Escape",
            description = "Experience the epitome of luxury with Dubai Luxury Escape, your ultimate guide to exploring one of the world’s most glamorous destinations. This app features curated tours of Dubai’s iconic landmarks, from the towering Burj Khalifa to the man-made Palm Jumeirah island. Explore the city’s opulent shopping malls, including the Dubai Mall and Mall of the Emirates, offering everything from high-end fashion to indoor ski slopes. Enjoy world-class dining with recommendations for Michelin-starred restaurants and local eateries serving Middle Eastern delicacies. Take a desert safari or relax in a luxurious beachfront resort with private villas. The app also includes guides to Dubai’s vibrant nightlife, from exclusive rooftop bars to extravagant nightclubs. Whether you’re visiting for a luxury vacation or a business trip, Dubai Luxury Escape ensures you experience the city in style.",
            price = 2499.99,
            imageRes = R.drawable.dubai_luxery,
            rating = 4.8f,
            location = "Dubai, UAE",
            duration = "7 days",
            included = listOf(
                "Traditional tea ceremony experience",
                "Guided temple tours",
                "Luxury ryokan accommodation",
                "Bullet train passes"
            )
        ),
        Product(
            id = 10,
            name = "New York City Break",
            description = "Discover the magic of New York City with the ultimate city break guide. Explore iconic landmarks like the Statue of Liberty, Empire State Building, and Times Square, while also delving into the unique neighborhoods that give NYC its vibrant character. From the artistic streets of SoHo to the upscale avenues of the Upper East Side, New York City Break offers personalized itineraries for every kind of traveler. Enjoy world-class theater on Broadway, visit renowned museums like the Met and MoMA, or relax in Central Park. The app also provides insider tips for the best food experiences, from food trucks to Michelin-star restaurants. Whether it’s your first visit or you’re a seasoned traveler, this app helps you navigate the city with ease, offering subway maps, bus routes, and walking tours.",
            price = 2599.99,
            imageRes = R.drawable.new_york,
            rating = 4.9f,
            location = "New York City, USA",
            duration = "6 days",
            included = listOf(
                "Caldera view accommodation",
                "Wine tasting tour",
                "Sunset sailing cruise",
                "Private transfers"
            )
        ),
        Product(
            id = 11,
            name = "Rome Historical Tour",
            description = "Step back in time with the Rome Historical Tour app, designed to help you explore the Eternal City’s rich history. Walk through ancient ruins like the Colosseum, Roman Forum, and the Pantheon, while learning about the city’s role as the heart of the Roman Empire. This app features in-depth guides to historical landmarks, including Vatican City, St. Peter’s Basilica, and the Sistine Chapel. Discover Renaissance art at the Vatican Museums or take a stroll through Rome’s beautiful piazzas, such as Piazza Navona and Piazza di Spagna. Enjoy traditional Italian cuisine with restaurant recommendations ranging from local trattorias to fine dining experiences. The app also offers itineraries for day trips to nearby historical sites like Pompeii and Ostia Antica. With detailed maps and cultural insights, Rome Historical Tour brings history to life.",
            price = 1899.99,
            imageRes = R.drawable.rome_historical_tour,
            rating = 4.9f,
            location = "Rome, Italy",
            duration = "5 days",
            included = listOf(
                "Guided Inca Trail trek",
                "Mountain equipment",
                "Local expert guides",
                "Traditional accommodation"
            )
        ),
        Product(
            id = 12,
            name = "Maldives Paradise Retreat",
            description = "Immerse yourself in the tranquility of the Maldives with this exclusive app designed for luxury and relaxation. Explore crystal-clear waters, white sandy beaches, and some of the world’s most luxurious resorts. The Maldives Paradise Retreat app provides you with tailored itineraries for a dream vacation, whether you’re on a romantic honeymoon or a family getaway. Discover the best overwater villas, private beaches, and five-star dining experiences. The app also offers guides for snorkeling, scuba diving, and other water sports to explore the Maldives' vibrant coral reefs and marine life. Indulge in world-class spa treatments and wellness retreats to rejuvenate both body and soul. With insider tips on the best times to visit and how to navigate between islands, this app ensures a seamless and unforgettable experience.",
            price = 3499.99,
            imageRes = R.drawable.maladivs,
            rating = 4.8f,
            location = "Maldives",
            duration = "7 days",
            included = listOf(
                "Luxury safari lodges",
                "Game drives",
                "Professional wildlife guide",
                "All meals included"
            )
        )
    )

    fun getAllProducts(): List<Product> {
        return products
    }
}