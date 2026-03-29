# 🦕 Chrome Dinosaur (Java)

A faithful **Chrome Dinosaur clone developed in Java using Swing**, featuring all classic mechanics: run animation, ducking, animated birds, big cacti, scrolling track, clouds, progressive difficulty, and a persistent high score

---

## 📷 Preview

<img width="925" height="346" alt="image" src="https://github.com/user-attachments/assets/747cdd6b-07e7-4066-9a60-b14392414575" />

---

## ✅ Features

| Feature | Status |
|---|---|
| Dinosaur jump (UP key) | ✅ |
| Wall / ground collision | ✅ |
| Run animation (dino-run1/2.png) | ✅ |
| **Duck mechanic** (DOWN key, dino-duck1/2.png) | ✅ |
| Small cactus obstacles (3 variants) | ✅ |
| **Big cactus obstacles** (3 variants) | ✅ |
| **Animated bird obstacles** (bird1/2.png) | ✅ |
| **Scrolling ground track** (track.png) | ✅ |
| **Scrolling clouds** (cloud.png) | ✅ |
| **Game-over overlay** (game-over.png + reset.png) | ✅ |
| **Restart after game over** (ENTER / SPACE) | ✅ |
| **Progressive speed** (scales with score) | ✅ |
| **High score display** | ✅ |

---

## ⌨️ Controls

| Key | Action |
|---|---|
| ↑ | Jump |
| ↓ | Duck (hold) |
| `ENTER` or `SPACE` | Restart after game over |

---

## 🏆 Scoring

Score increments every frame. Speed increases every 500 points, capping at `MAX_SPEED = 20`. Birds begin spawning after score 500.

---

## ▶️ Run the Game

### 1️⃣ Clone the repository

```
git clone git@github.com:NourBelghazi/ChromeDinosaur.git
```

### 2️⃣ IntelliJ IDEA

1. Open the project folder.
2. Mark `src/` as the **Sources Root**.
3. Run `App.java`.

### 3️⃣ Command line

```bash
javac -d out -sourcepath src src/App.java
java -cp out:src App
```

---

## 📁 Project structure

```
Dinosaure-Google/
├── src/
│   ├── App.java                         # Entry point – creates the JFrame
│   └── dino/
│       ├── GameConstants.java           # All game-wide constants
│       ├── ImageLoader.java             # Utility – loads PNG sprites from classpath
│       ├── GamePanel.java               # JPanel + Timer – wires engine, renderer, input
│       ├── entity/
│       │   └── Block.java               # Game entity (position, size, sprite, collision)
│       ├── engine/
│       │   └── GameEngine.java          # All game logic (movement, collision, scoring)
│       ├── renderer/
│       │   └── GameRenderer.java        # draw() + drawHUD() + game-over overlay
│       └── input/
│           └── InputHandler.java        # KeyAdapter – delegates to GameEngine
└── src/images/
    ├── dino-run1.png / dino-run2.png    # Run animation frames
    ├── dino-jump.png / dino-dead.png    # Jump and dead sprites
    ├── dino-duck1.png / dino-duck2.png  # Duck animation frames
    ├── cactus1/2/3.png                  # Small cactus variants
    ├── big-cactus1/2/3.png              # Big cactus variants
    ├── bird1.png / bird2.png            # Bird animation frames
    ├── track.png                        # Scrolling ground
    ├── cloud.png                        # Background cloud
    ├── game-over.png                    # Game over text overlay
    └── reset.png                        # Restart button icon
```

---

## 🧠 Architecture overview

Each class has a **single responsibility**:

| Class | Responsibility |
|---|---|
| `App` | Creates the window |
| `GamePanel` | JPanel shell – owns the Timers and wires components |
| `GameConstants` | Single source of truth for all numeric constants |
| `ImageLoader` | Loads PNG images from the classpath |
| `Block` | Data: position, size, sprite; static `collides()` |
| `GameEngine` | All game state and logic (move, score, difficulty) |
| `GameRenderer` | Reads engine state, draws everything via `Graphics` |
| `InputHandler` | Translates key events into `GameEngine` method calls |

**Game loop:** `javax.swing.Timer` fires every **~16 ms** (60 fps) → `GameEngine.move()` + `repaint()`.

---

## 🎮 Game mechanics

### Run animation
Alternates between `dino-run1.png` and `dino-run2.png` every 8 frames while the dinosaur is on the ground.

### Duck mechanic
Holding DOWN while grounded shrinks the hitbox to `118×60` and switches to the duck animation. Releasing DOWN restores full height.

### Bird obstacles
Birds appear after score 500 at three possible heights (low, mid, high). They animate between `bird1.png` and `bird2.png`. Players must jump over low birds or duck under high ones.

### Progressive difficulty
`speed = min(INITIAL_SPEED + score / 500, MAX_SPEED)`. Both obstacles and the scrolling track accelerate uniformly.

### Scrolling track & clouds
Two copies of `track.png` tile seamlessly. Clouds scroll at half speed for a parallax effect.

---

## 🗺️ Obstacle types

| Obstacle | Condition |
|---|---|
| Small cactus (3 variants) | Always |
| Big cactus (3 variants) | 30% chance |
| Bird (3 heights) | After score 500, 25% chance |

---

## ⚙️ Technologies Used

- **Java** + **Java Swing**
- `JPanel` rendering with `paintComponent()`
- Keyboard input with `KeyAdapter`
- Game loop with `ActionListener` + `Timer`

---

## 🚀 Possible Improvements

- Sound effects
- Day / night mode transition
- Score-based milestone banners
- Multiple difficulty presets

---

## 👨‍💻 Author

**Nour Belghazi**
