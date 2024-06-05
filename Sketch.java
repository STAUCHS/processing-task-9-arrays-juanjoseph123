import processing.core.PApplet;

public class Sketch extends PApplet {
  // Related arrays for the (x, y) coordinates and visibility state of the snowflakes
  float[] snowX = new float[42];
  float[] snowY = new float[42];
  boolean[] snowHidden = new boolean[42]; // To store the visibility state of each snowflake
  int snowDiameter = 10; // Diameter of the snowflakes
  float speed = 2; // Speed at which the snowflakes fall
  int lives = 3; // Player's lives
  float playerX, playerY; // Player's position
  float playerWidth = 20; // Player's width
  float playerHeight = 30; // Player's height

  public void settings() {
    size(400, 400); // Set the size of the window
  }

  public void setup() {
    playerX = width / 2; 
    playerY = height - 30; 
    for (int i = 0; i < snowX.length; i++) {
      snowX[i] = random(width); 
      snowY[i] = random(-height, 0); 
      snowHidden[i] = false; 
    }
  }

  public void draw() {
    background(0); 
    drawSnowflakes(); 
    drawPlayer(); 
    drawLives(); 
    checkCollisions(); 

    // Check if the player has lost all lives
    if (lives <= 0) {
      background(255); 
      textSize(32); 
      fill(0); 
      textAlign(CENTER, CENTER); 
      text("Game Over", width / 2, height / 2); // Display "Game Over" message
      noLoop(); 
    }
  }

  public void drawSnowflakes() {
    for (int i = 0; i < snowX.length; i++) {
      if (!snowHidden[i]) {
        fill(255); 
        ellipse(snowX[i], snowY[i], snowDiameter, snowDiameter); // Draw the snowflake
        snowY[i] += speed; // Move the snowflake downwards

        // Reset snowflake position if it goes off-screen
        if (snowY[i] > height) {
          snowY[i] = 0;
          snowX[i] = random(width);
        }
      }
    }
  }

  public void drawPlayer() {
    fill(0, 0, 255); 
    ellipse(playerX, playerY, playerWidth, playerHeight); // Draw the player as an ellipse
  }

  public void drawLives() {
    fill(255, 0, 0); 
    for (int i = 0; i < lives; i++) {
      rect(width - 30 - i * 20, 10, 15, 15); // Draw a rectangle for each life
    }
  }

  public void checkCollisions() {
    for (int i = 0; i < snowX.length; i++) {
      // Check if a snowflake hits the player and is visible
      if (!snowHidden[i] && dist(playerX, playerY, snowX[i], snowY[i]) < (snowDiameter / 2 + max(playerWidth, playerHeight) / 2)) {
        lives--; // Decrease lives
        snowHidden[i] = true; // Hide the snowflake
      }
    }
  }

  public void keyPressed() {
    // Increase or decrease the falling speed of snowflakes
    if (keyCode == DOWN) {
      speed += 1;
    } else if (keyCode == UP) {
      speed = max(1, speed - 1);
    }
    // Move the player using WASD keys
    if (key == 'w' || key == 'W') {
      playerY -= 5;
    }
    if (key == 's' || key == 'S') {
      playerY += 5;
    }
    if (key == 'a' || key == 'A') {
      playerX -= 5;
    }
    if (key == 'd' || key == 'D') {
      playerX += 5;
    }
  }

  public void mousePressed() {
    // Hide snowflakes when clicked
    for (int i = 0; i < snowX.length; i++) {
      if (!snowHidden[i] && dist(mouseX, mouseY, snowX[i], snowY[i]) < snowDiameter / 2) {
        snowHidden[i] = true;
      }
    }
  }

  public static void main(String[] args) {
    PApplet.main("Sketch"); 
  }
}
