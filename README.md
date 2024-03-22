# Watson's Revenge

## About The Project

"Watson's Revenge" is a mobile treasure hunt app inspired by the legendary tales of Sherlock Holmes and his assistant John Watson. Set against the backdrop of London, this app offers users a unique adventure through the city, leveraging location services to embark on a clue-based treasure hunt.

### Features

- **Location-Based Clues:** Utilizes the device's location services to present the user with clues based on their current location.
- **Interactive Clues:** Allows users to request hints for each clue, aiding in their adventure.
- **Progress Tracking:** Implements the Haversine formula to check the user's proximity to the clue's location, ensuring they are within a 50-meter radius before advancing.
- **Pause and Resume:** Users can pause their adventure after finding a clue and resume at their convenience.
- **Completion Celebration:** Upon solving all clues, users are directed to a page celebrating their success with additional insights on the final clue.

## Getting Started

This section provides instructions on setting up "Watson's Revenge" locally. To get a local copy up and running, follow these simple steps.

### Prerequisites

- Kotlin development environment (e.g., Android Studio)
- Android device or emulator with GPS capabilities

### Installation

1. Clone the repo
   ```bash
   git clone https://github.com/yourusername/watsons-revenge.git
   ```
2. Open the project in Android Studio
3. Sync the Gradle files
4. Build and run the project on your device or emulator

### Usage

To start your adventure with "Watson's Revenge," grant the app permission to use location services. The treasure hunt begins with a timer and the presentation of the first clue. Navigate through London's streets and landmarks by solving clues and discovering new ones until you complete the hunt.

1. Open the app on your device
2. Grant location permissions
3. Start the adventure
4. Use the clues and hints to find the landmarks
5. Find all successive landmarks to complete the hunt

### Contributing

Contributions are what make the open-source community such an amazing place to learn, inspire, and create. Any contributions you make are greatly appreciated.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request
