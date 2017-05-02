package SuperMario;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/*
 * In this example, I'm going to show you how I would handle reading in from
 * a level text file. In this example, the reading in is handled in the constructor
 * of this random object I've made; but the reality is you can put the reading
 * code in its own method or anywhere else you want. It'll have to read a file once
 * per level, so keep that in mind.
 */
public class Mario_Madness extends JFrame implements KeyListener {

	private static final long serialVersionUID = 1L;
	static boolean LVL1_1 = true;
	static boolean LVL1_2 = false;
	static boolean LVL1_3 = false;
	static boolean LVL1_4 = false;
	static boolean StartScreen = true;
	private boolean Bonus1_1 = false;
	private boolean Bonus1_2 = false;
	private boolean Reset = true;
	static boolean jumpSoundPlayed = false;
	static boolean mushroomSpawned = false;
	private Color color;
	private Color defaultColor;
	long startTime = System.currentTimeMillis();
	double gameTime;

	private static int JumpMax = 180;
	private int JumpCheck = 0;
	private boolean isOnGround = true;
	static boolean bounced = true;
	private int kills = 0;
	private int coins = 0;
	private long timer = 0;
	private int score = 0;

	static Clip sound, music;
	static boolean musicStarted = false;
	static boolean FlagSound = false;
	static boolean StageCleared = false;

	static boolean left = false;
	static boolean right = false;
	boolean up = false;
	static boolean duck = false;

	private static boolean down = true;
	static boolean jump = false;
	static boolean walking = false;
	private boolean pressedJump = false;
	private boolean speedBoost = false;
	static int animationTime = 0;

	String Level1_1 = "res/Levels/LevelOne.txt";
	String Level1_2 = "res/Levels/LevelTwo.txt";
	String Level1_3 = "res/Levels/LevelThree.txt";
	String Level1_4 = "res/Levels/LevelFour.txt";
	String BonusLevel1_1 = "res/Levels/BonusLevel.txt";
	String BonusLevel1_2 = "res/Levels/BonusLevel2.txt";
	String CurrentLevel;

	String MainTheme = "res/Sounds/Main Theme 16bit.wav";
	String UnderWorld = "res/Sounds/UnderWorld 16bit.wav";
	String CastleTheme = "res/Sounds/Castle Theme 16bit.wav";

	static String CoinSound = "res/Sounds/Coin.wav";
	static String BlockBump = "res/Sounds/Block Bump.wav";
	static String PowerUpAppears = "res/Sounds/Powerup Appears.wav";
	static String PowerUp = "res/Sounds/Powerup.wav";
	String SmallJump = "res/Sounds/Small Jump.wav";
	String MarioDies = "res/Sounds/Mario Dies.wav";
	String FlagPole = "res/Sounds/Flagpole.wav";
	String StageClear = "res/Sounds/Stage Clear.wav";
	String Pipe = "res/Sounds/Pipe.wav";
	static String ShallNotPass = "res/Sounds/Shall Not Pass 16bit.wav";

	/*
	 * This ArrayList holds every object that we're going to draw to the screen.
	 */
	public static ArrayList<ScreenObject> objects;
	public static ArrayList<ScreenObject> background;
	public static Actor Mario;

	/*
	 * These two integers can be anything you want. They control the size of the
	 * game screen. Keep in mind, the game screen is DIFFERENT from the frame
	 * size. These values will always be the same, but if the user scales the
	 * frame around, I have this programmed so that the draw method will
	 * automatically scale the game window with it- that means, that the game
	 * window is technically always the same size, and is scaled up or down
	 * AFTER all the drawing is done to fit the frame.
	 */
	final static int GAME_WIDTH = 1000;
	final static int GAME_HEIGHT = 640;
	final static int CUBE_HEIGHT = 40;
	final static int CUBE_WIDTH = 40;

	/*
	 * When we call draw is the interesting part. First we make a blank slate
	 * image we can draw on. By default, it is just a large white canvas. You'll
	 * notice that I set the width and height to our final variables. The screen
	 * size will ALWAYS be the same, and will be scaled up in just a bit. THis
	 * makes it so that coordinate tracking for our objects is always the same,
	 * too.
	 */
	BufferedImage raster = new BufferedImage(GAME_WIDTH, GAME_HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);
	/*
	 * This line creates a graphics context for the blank image we just made.
	 * We will use this image too do all of our drawing.
	 */
	Graphics2D g2d = raster.createGraphics();

	/*
	 * Just the code to run the game. Nothing special.
	 */
	public static void main(String[] args) throws IOException {
		Mario_Madness Game = new Mario_Madness();

		Game.pack();
		Game.setLocationRelativeTo(null);
		Game.setVisible(true);
		Game.loop();
	}// end of main

	/**
	 * 
	 */
	public Mario_Madness() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addKeyListener(this);
		setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
	}// end of constructor

	/**
	 * 
	 * @param song
	 */
	public static void playSound(String song) {
		try {
			AudioInputStream sound = AudioSystem.getAudioInputStream(new File(song));
			Mario_Madness.sound = AudioSystem.getClip();
			Mario_Madness.sound.open(sound);
			Mario_Madness.sound.start();
		} catch (Exception ex) {
			System.out.println("Error with playing sound.");
			ex.printStackTrace();
		}
	}

	/**
	 * 
	 * @param song
	 */
	public static void playMusic(String song) {
		try {
			AudioInputStream sound = AudioSystem.getAudioInputStream(new File(song));
			Mario_Madness.music = AudioSystem.getClip();
			Mario_Madness.music.open(sound);
			Mario_Madness.music.start();
		} catch (Exception ex) {
			System.out.println("Error with playing sound.");
			ex.printStackTrace();
		}
	}

	/**
	 * 
	 * @throws IOException
	 */
	public void loadLevel() throws IOException {
		Reset = false;
		/*
		 * First, we have to initialize the object list at some point.
		 */
		objects = new ArrayList<ScreenObject>();
		background = new ArrayList<ScreenObject>();
		/*
		 * Now we're going to fill our object list with the level's tiles.
		 */

		if (LVL1_1)
			CurrentLevel = Level1_1;
		if (LVL1_2)
			CurrentLevel = Level1_2;
		if (LVL1_3)
			CurrentLevel = Level1_3;
		if (LVL1_4)
			CurrentLevel = Level1_4;
		if (Bonus1_1)
			CurrentLevel = BonusLevel1_1;
		if (Bonus1_2)
			CurrentLevel = BonusLevel1_2;

		final File levelLayout = new File(CurrentLevel);
		if (!levelLayout.exists()) {
			System.out.println("file does not exists");
			System.exit(2);
		}

		/*
		 * This try block creates a scanner object that will go through our file
		 * line by line.
		 */
		try (Scanner levelReader = new Scanner(levelLayout)) {
			/*
			 * These variables keep track of where on the screen our objects
			 * should go.
			 */
			int x = 0, y = 0;
			/*
			 * THis while loop will check to see if there's a line in the source
			 * file available for scanning. As long as the text file isn't
			 * finished being read, this while loop will progress.
			 */
			while (levelReader.hasNextLine()) {
				/*
				 * First, we will grab one entire line of the text file.
				 */
				String s = levelReader.nextLine();
				/*
				 * Then we check to see if the line is a comment. If it is, skip
				 * to the next line.
				 */
				if (s.charAt(0) == '#')
					continue;
				/*
				 * If it's not a comment, we need to convert the string to a
				 * char array and go through each and every character in the
				 * text file, and put an object at an x and y position
				 * corresponding to its place in the text file. So for example,
				 * if we have the char S, a brick, and our x is 10 and y is 3,
				 * we should create a brick object at 10 * 10, 3 * 10. THe two
				 * tens that I multiplied by are to provide space between the
				 * objects. Really, those two tens should actually be the height
				 * and width of your tiles.
				 */
				for (char c : s.toCharArray()) {
					/*
					 * If we find an S, place a brick at our x and y position in
					 * the file. Again the * 10 part is to space the objects
					 * out, since we're giving them a precise pixel position. If
					 * we didn't multiply by the height and width of the images,
					 * then they'd all be clustered in one spot.
					 */
					switch (c) {
					case '~':
						objects.add(new ScreenStarter(x * CUBE_WIDTH, y * CUBE_HEIGHT));
						break;
					case 'S':
						if (LVL1_1)
							objects.add(new Brick(x * CUBE_WIDTH, y * CUBE_HEIGHT));
						if (LVL1_2 || Bonus1_1 || Bonus1_2)
							objects.add(new BlueBrick(x * CUBE_WIDTH, y * CUBE_HEIGHT));
						if (LVL1_3)
							objects.add(new TreeTrunk(x * CUBE_WIDTH, y * CUBE_HEIGHT));
						break;
					case 'G':
						if (LVL1_1 || LVL1_3)
							objects.add(new Ground(x * CUBE_WIDTH, y * CUBE_HEIGHT));
						if (LVL1_2 || Bonus1_1 || Bonus1_2)
							objects.add(new BlueGround(x * CUBE_WIDTH, y * CUBE_HEIGHT));
						if (LVL1_4)
							objects.add(new GrayBrick(x * CUBE_WIDTH, y * CUBE_HEIGHT));
						break;
					case 'Q':
						objects.add(new CoinBlock(x * CUBE_WIDTH, y * CUBE_HEIGHT));
						break;
					case 'c':
						background.add(new Cloud(x * CUBE_WIDTH, y * CUBE_HEIGHT));
						break;
					case 'C':
						if (LVL1_1 || LVL1_3)
							objects.add(new Coin(x * CUBE_WIDTH, y * CUBE_HEIGHT));
						if (LVL1_2 || Bonus1_1 || Bonus1_2)
							objects.add(new BlueCoin(x * CUBE_WIDTH, y * CUBE_HEIGHT));
						break;
					case 'g':
						if (LVL1_1)
							objects.add(new Goomba(x * CUBE_WIDTH, y * CUBE_HEIGHT));
						if (LVL1_2)
							objects.add(new BlueGoomba(x * CUBE_WIDTH, y * CUBE_HEIGHT));
						break;
					case 'H':
						background.add(new Hill(x * CUBE_WIDTH, y * CUBE_HEIGHT));
						break;
					case 'B':
						background.add(new Bush(x * CUBE_WIDTH, y * CUBE_HEIGHT));
						break;
					case 's':
						if (LVL1_1 || LVL1_3)
							objects.add(new Block(x * CUBE_WIDTH, y * CUBE_HEIGHT));
						if (LVL1_2)
							objects.add(new BlueBlock(x * CUBE_WIDTH, y * CUBE_HEIGHT));
						if (LVL1_4)
							objects.add(new Bridge(x * CUBE_WIDTH, y * CUBE_HEIGHT));
						break;
					case 'P':
						objects.add(new Pipe(x * CUBE_WIDTH, y * CUBE_HEIGHT));
						break;
					case 'E':
						background.add(new Castle(x * CUBE_WIDTH, y * CUBE_HEIGHT));
						break;
					case 'F':
						if (LVL1_1 || LVL1_3)
							background.add(new Flag(x * CUBE_WIDTH, y * CUBE_HEIGHT));
						break;
					case 'O':
						objects.add(new LongPipe(x * CUBE_WIDTH, y * CUBE_HEIGHT));
						break;
					case 'o':
						if (Bonus1_1 || LVL1_2 || Bonus1_2)
							objects.add(new SidePipe(x * CUBE_WIDTH, y * CUBE_HEIGHT));
						if (LVL1_4)
							background.add(new LavaFireBall(x * CUBE_WIDTH, y * CUBE_HEIGHT));
						break;
					case 'p':
						objects.add(new MagicMushroom(x * CUBE_WIDTH, y * CUBE_HEIGHT));
						objects.add(new FireFlower(x * CUBE_WIDTH, y * CUBE_HEIGHT));
						objects.add(new PowerUpBlock(x * CUBE_WIDTH, y * CUBE_HEIGHT));
						break;
					case 't':
						objects.add(new Star(x * CUBE_WIDTH, y * CUBE_HEIGHT));
						objects.add(new StarBlock(x * CUBE_WIDTH, y * CUBE_HEIGHT));
						break;
					case 'e':
						objects.add(new Elevator(x * CUBE_WIDTH, y * CUBE_HEIGHT));
						break;
					case 'u':
						objects.add(new UpElevator(x * CUBE_WIDTH, y * CUBE_HEIGHT));
						break;
					case 'd':
						objects.add(new DownElevator(x * CUBE_WIDTH, y * CUBE_HEIGHT));
						break;
					case 'q':
						if (LVL1_1)
							objects.add(new CoinBrick(x * CUBE_WIDTH, y * CUBE_HEIGHT));
						if (LVL1_2 || Bonus1_2)
							objects.add(new BlueCoinBrick(x * CUBE_WIDTH, y * CUBE_HEIGHT));
						break;
					case 'L':
						if (LVL1_3)
							objects.add(new TreeTopLeft(x * CUBE_WIDTH, y * CUBE_HEIGHT));
						if (LVL1_4)
							background.add(new LavaTop(x * CUBE_WIDTH, y * CUBE_HEIGHT));
						break;
					case 'l':
						background.add(new LavaBottom(x * CUBE_WIDTH, y * CUBE_HEIGHT));
						break;
					case 'T':
						if (LVL1_3)
							objects.add(new TreeTop(x * CUBE_WIDTH, y * CUBE_HEIGHT));
						break;
					case 'R':
						objects.add(new TreeTopRight(x * CUBE_WIDTH, y * CUBE_HEIGHT));
						break;
					case 'n':
						objects.add(new BonusEntrance(x * CUBE_WIDTH, y * CUBE_HEIGHT));
						break;
					case 'x':
						objects.add(new BonusExit(x * CUBE_WIDTH, y * CUBE_HEIGHT));
						break;
					case '2':
						if (LVL1_1)
							objects.add(new Koopa(x * CUBE_WIDTH, y * CUBE_HEIGHT));
						if (LVL1_2)
							objects.add(new BlueKoopa(x * CUBE_WIDTH, y * CUBE_HEIGHT));
						if (LVL1_4)
							objects.add(new ProfGanLaugh(x * CUBE_WIDTH, y * CUBE_HEIGHT));
						break;
					case '3':
						objects.add(new RedKoopa(x * CUBE_WIDTH, y * CUBE_HEIGHT));
						break;
					case '4':
						objects.add(new BluePirahna(x * CUBE_WIDTH, y * CUBE_HEIGHT));
						break;
					case '5':
						objects.add(new RedKoopaTroopa(x * CUBE_WIDTH, y * CUBE_HEIGHT));
						break;
					}// End of Switch

					/*
					 * This x++ increments our x position by 1 every time we
					 * scan a character.
					 */
					x++;
				} // End of char array
				
				/*
				 * After we're done scanning a line, we move to the left
				 * side of the next line, so our x should reset to 0, 
				 * and our y should increment one. 
				 */
				
				x = 0;
				y++;
			} // End of while loop for scanner
			
			/*
			 * We have to include a catch statement for File. If it can't
			 * find the level file, it'll show a JOptionPane saying that.
			 */
		} catch (FileNotFoundException fnfe) {
			JOptionPane.showMessageDialog(null, "Text file not found.");
		}

		if (LVL1_1 || LVL1_3)
			Mario = new Mario((GAME_WIDTH / 2), (GAME_HEIGHT - (CUBE_HEIGHT * 3)));
		if (LVL1_2 || LVL1_4 || Bonus1_1 || Bonus1_2)
			Mario = new Mario(100, 100);
		if (LVL1_4)
			Mario = new Mario(100, 280);
	}// End of LoadLevel Method
	// *************************

	public static void Bounce(Actor m) {

		int start = 0;
		int end = 20;

		if (start < end) {
			m.setY(m.getY() - 5);
			start += 5;
		}
	}

	public boolean rightCollision(ArrayList<ScreenObject> o, Actor m) {
		if (!left) {
			for (ScreenObject so : o) {
				if (so instanceof Ground || so instanceof BlueGround || so instanceof BlueBlock || so instanceof Block
						|| so instanceof CoinBlock || so instanceof Brick || so instanceof BlueBrick
						|| so instanceof Steel || so instanceof BlueSteel) {
					if (m.getY() + CUBE_HEIGHT > so.getY() && m.getY() < so.getY() + CUBE_HEIGHT) {
						if ((m.getX() + CUBE_WIDTH > (so.getX() - 3))
								&& (m.getX() + CUBE_WIDTH < so.getX() + CUBE_WIDTH / 2)) {
							return true;
						}
					}
				} else if (so instanceof Pipe || so instanceof BonusEntrance || so instanceof BonusExit) {
					if (m.getY() + CUBE_HEIGHT > so.getY() && m.getY() < so.getY() + CUBE_HEIGHT * 4) {
						if ((m.getX() + CUBE_WIDTH > (so.getX()))
								&& (m.getX() + CUBE_WIDTH < so.getX() + CUBE_WIDTH / 2)) {
							return true;
						}
					}
				} else if (so instanceof UpElevator || so instanceof DownElevator || so instanceof Elevator) {
					if (m.getY() + CUBE_HEIGHT > so.getY() && m.getY() < so.getY() + CUBE_HEIGHT) {
						if ((m.getX() + CUBE_WIDTH > (so.getX() - 3))
								&& (m.getX() + CUBE_WIDTH < so.getX() + CUBE_WIDTH)) {
							return true;
						}
					}
				} else if (so instanceof SidePipe) {
					if (m.getY() + CUBE_HEIGHT > so.getY() && m.getY() < so.getY() + CUBE_HEIGHT * 4) {
						if ((m.getX() + CUBE_WIDTH > (so.getX()))
								&& (m.getX() + CUBE_WIDTH < so.getX() + CUBE_WIDTH / 2)) {
							playSound(Pipe);
							if (LVL1_2) {
								LVL1_2 = false;
								LVL1_3 = true;
								music.stop();
								musicStarted = false;
								Reset = true;
								try {
									Thread.sleep(1000);
								} catch (Exception e) {
								}
							} else if (Bonus1_1) {
								LVL1_1 = true;
								Bonus1_1 = false;
								music.stop();
								musicStarted = false;
								Reset = true;
								try {
									Thread.sleep(1000);
								} catch (Exception e) {
								}
							} else if (Bonus1_2) {

							}
						}
					}
				} else if (so instanceof Coin || so instanceof BlueCoin || so instanceof MagicMushroom) {
					if (m.getY() + CUBE_HEIGHT > so.getY() && m.getY() < so.getY() + CUBE_HEIGHT) {
						if (m.getX() + CUBE_WIDTH >= so.getX() && m.getX() <= so.getX() + CUBE_WIDTH) {
							if (so instanceof Coin || so instanceof BlueCoin) {
								playSound(CoinSound);
								objects.remove(so);
								coins++;
								score += 200;
							}
							if (so instanceof MagicMushroom && mushroomSpawned) {
								playSound(PowerUp);
								m.powerUpAnimation();
								mushroomSpawned = false;
								objects.remove(so);
							}
							return true;
						}
					}
				}
			}
		}
		return false;
	}// end of rightCollision method

	public boolean leftCollision(ArrayList<ScreenObject> o, Actor m) {
		if (!right) {
			for (ScreenObject so : o) {
				if (so instanceof Ground || so instanceof BlueGround || so instanceof BlueBlock || so instanceof Block
						|| so instanceof CoinBlock || so instanceof Brick || so instanceof BlueBrick
						|| so instanceof Steel || so instanceof BlueSteel) {
					if (m.getY() + CUBE_HEIGHT > so.getY() && m.getY() < so.getY() + CUBE_HEIGHT) {
						if ((m.getX() < (so.getX() + CUBE_WIDTH)) && (m.getX() > so.getX() + CUBE_WIDTH / 2)) {
							return true;
						}
					}
				} else if (so instanceof Pipe || so instanceof BonusEntrance || so instanceof BonusExit) {
					if (m.getY() + CUBE_HEIGHT > so.getY() && m.getY() < so.getY() + CUBE_HEIGHT * 4) {
						if ((m.getX() < (so.getX() + CUBE_WIDTH * 2)) && (m.getX() > so.getX() + CUBE_WIDTH)) {
							return true;
						}
					}
				} else if (so instanceof UpElevator || so instanceof DownElevator || so instanceof Elevator) {
					if (m.getY() + CUBE_HEIGHT > so.getY() && m.getY() < so.getY() + CUBE_HEIGHT) {
						if ((m.getX() < (so.getX() + CUBE_WIDTH * 3)) && (m.getX() > so.getX() + CUBE_WIDTH)) {
							return true;
						}
					}
				} else if (so instanceof Coin || so instanceof BlueCoin || so instanceof MagicMushroom) {
					if (m.getY() + CUBE_HEIGHT > so.getY() && m.getY() < so.getY() + CUBE_HEIGHT) {
						if (m.getX() + CUBE_WIDTH >= so.getX() && m.getX() <= so.getX() + CUBE_WIDTH) {
							if (so instanceof Coin || so instanceof BlueCoin) {
								playSound(CoinSound);
								objects.remove(so);
								coins++;
								score += 200;
							}
							if (so instanceof MagicMushroom && mushroomSpawned) {
								playSound(PowerUp);
								m.powerUpAnimation();
								mushroomSpawned = false;
								objects.remove(so);
							}
							return true;
						}
					}
				}
			}
		}
		return false;
	}// end of leftCollision method

	public boolean floorCollision(ArrayList<ScreenObject> o, Actor m) {
		if (down) {
			for (ScreenObject so : o) {
				if (so instanceof Ground || so instanceof BlueGround || so instanceof BlueBlock || so instanceof Block
						|| so instanceof CoinBlock || so instanceof Brick || so instanceof BlueBrick
						|| so instanceof GrayBrick || so instanceof TreeTopLeft || so instanceof TreeTop
						|| so instanceof TreeTopRight || so instanceof Steel || so instanceof BlueSteel) {
					if (m.getX() + CUBE_WIDTH / 2 >= so.getX() && m.getX() + CUBE_WIDTH / 2 <= so.getX() + CUBE_WIDTH) {
						if (m.big) {
							if (m.getY() + CUBE_HEIGHT * 2 >= so.getY()
									&& m.getY() + CUBE_HEIGHT * 2 <= so.getY() + CUBE_HEIGHT * 2) {
								m.setY(so.getY() - CUBE_HEIGHT * 2);
								jumpSoundPlayed = false;
								isOnGround = true;
								return true;
							}
						} else {
							if (m.getY() + CUBE_HEIGHT >= so.getY()
									&& m.getY() + CUBE_HEIGHT <= so.getY() + CUBE_HEIGHT) {
								m.setY(so.getY() - CUBE_HEIGHT);
								jumpSoundPlayed = false;
								isOnGround = true;
								return true;
							}
						}
					}
				} else if (so instanceof Pipe || so instanceof BonusExit || so instanceof SidePipe
						|| so instanceof LongPipe) {
					if (m.getX() + CUBE_WIDTH > so.getX() && m.getX() < so.getX() + CUBE_WIDTH * 2) {
						if (m.getY() + CUBE_HEIGHT >= so.getY()
								&& m.getY() + CUBE_HEIGHT <= so.getY() + CUBE_HEIGHT / 2) {
							m.setY(so.getY() - CUBE_HEIGHT);
							jumpSoundPlayed = false;
							isOnGround = true;
							return true;
						}
					}
				} else if (so instanceof UpElevator || so instanceof DownElevator || so instanceof Elevator) {
					if (m.getX() + CUBE_WIDTH > so.getX() && m.getX() < so.getX() + CUBE_WIDTH * 3) {
						if (m.getY() + CUBE_HEIGHT >= so.getY() && m.getY() + CUBE_HEIGHT < so.getY() + CUBE_HEIGHT) {
							m.setY(so.getY() - CUBE_HEIGHT);
							jumpSoundPlayed = false;
							isOnGround = true;
							return true;
						}
					}
				} else if (so instanceof Goomba || so instanceof BlueGoomba || so instanceof Koopa
						|| so instanceof BlueKoopa || so instanceof RedKoopa) {
					if (m.getX() + Mario_Madness.CUBE_WIDTH > so.getX()
							&& m.getX() < so.getX() + Mario_Madness.CUBE_WIDTH) {
						if (m.getY() + Mario_Madness.CUBE_HEIGHT * 2 >= so.getY()
								&& m.getY() + Mario_Madness.CUBE_HEIGHT * 2 < so.getY() + Mario_Madness.CUBE_HEIGHT) {
							objects.remove(so);
							score += 200;
							kills++;
							bounced = true;
							return true;
						}
					}
				} else if (so instanceof Coin || so instanceof BlueCoin) {
					if (m.getX() + CUBE_WIDTH / 2 >= so.getX() && m.getX() + CUBE_WIDTH / 2 <= so.getX() + CUBE_WIDTH) {
						if (m.getY() + CUBE_HEIGHT >= so.getY() && m.getY() + CUBE_HEIGHT <= so.getY() + CUBE_HEIGHT) {
							playSound(CoinSound);
							objects.remove(so);
							coins++;
							return false;
						}
					}
				} else if (so instanceof BonusEntrance) {
					if (m.getX() + CUBE_WIDTH > so.getX() && m.getX() < so.getX() + CUBE_WIDTH * 2) {
						if (m.getY() + CUBE_HEIGHT >= so.getY()
								&& m.getY() + CUBE_HEIGHT <= so.getY() + CUBE_HEIGHT / 2) {
							if (duck) {
								Bonus1_1 = true;
								LVL1_1 = false;
								LVL1_2 = false;
								LVL1_3 = false;
								LVL1_4 = false;
								Bonus1_2 = false;
								Reset = true;
								music.stop();
								musicStarted = false;
							}
							return true;
						}
					}
				}
			}
		}
		return false;
	}// end of floorCollision method

	public boolean ceilingDetection(ArrayList<ScreenObject> o, Actor m) {
		if (jump) {
			for (ScreenObject so : o) {
				if (so instanceof CoinBlock || so instanceof CoinBrick || so instanceof BlueCoinBrick) {
					if (m.getX() + CUBE_WIDTH > so.getX() && m.getX() < so.getX() + CUBE_WIDTH) {
						if (m.getY() <= so.getY() + CUBE_HEIGHT && m.getY() >= so.getY() + CUBE_HEIGHT) {
							playSound(CoinSound);
							if (so instanceof CoinBlock) {
								objects.remove(so);
								coins++;
								score += 200;
								if (LVL1_1 || LVL1_3)
									objects.add(new Steel(so.getX(), so.getY()));
								if (LVL1_2)
									objects.add(new BlueSteel(so.getX(), so.getY()));
							}
							so.bumped = true;
							so.hits++;
							coins++;
							if (so.hits >= so.MaxHits) {
								objects.remove(so);
								if (LVL1_1 || LVL1_3)
									objects.add(new Steel(so.getX(), so.getY()));
								if (LVL1_2)
									objects.add(new BlueSteel(so.getX(), so.getY()));
							}

							down = true;
							return true;
						}
					}
				} else if (so instanceof Brick || so instanceof BlueBrick || so instanceof GrayBrick
						|| so instanceof Steel || so instanceof BlueSteel) {
					if (m.getX() + CUBE_WIDTH > so.getX() && m.getX() < so.getX() + CUBE_WIDTH) {
						if (m.getY() <= so.getY() + CUBE_HEIGHT && m.getY() >= so.getY() + CUBE_HEIGHT) {
							playSound(BlockBump);
							so.bumped = true;
							// objects.remove(so);
							down = true;
							return true;
						}
					}
				} else if (so instanceof UpElevator || so instanceof DownElevator || so instanceof Elevator) {
					if (m.getX() + CUBE_WIDTH > so.getX() && m.getX() < so.getX() + CUBE_WIDTH) {
						if (m.getY() > so.getY() && m.getY() <= so.getY() + CUBE_HEIGHT) {
							down = true;
							return true;
						}
					}
				} else if (so instanceof Coin || so instanceof BlueCoin) {
					if (m.getX() + CUBE_WIDTH > so.getX() && m.getX() < so.getX() + CUBE_WIDTH) {
						if (m.getY() <= so.getY() + CUBE_HEIGHT && m.getY() >= so.getY() + CUBE_HEIGHT) {
							playSound(CoinSound);
							objects.remove(so);
							coins++;
							score += 200;
							return false;
						}
					}
				} else if (so instanceof PowerUpBlock) {
					if (m.getX() + CUBE_WIDTH > so.getX() && m.getX() < so.getX() + CUBE_WIDTH) {
						if (m.getY() <= so.getY() + CUBE_HEIGHT && m.getY() >= so.getY() + CUBE_HEIGHT) {
							playSound(PowerUpAppears);
							objects.remove(so);
							score += 200;
							if (LVL1_1 || LVL1_3) {
								objects.add(new Steel(so.getX(), so.getY()));
							}
							if (LVL1_2) {
								objects.add(new BlueSteel(so.getX(), so.getY()));
							}
							mushroomSpawned = true;
							down = true;
							return true;
						}
					}
				}
			}
		}
		return false;
	}// end of ceilingDetection method

	// This loop represents the game logic loop. It tells every object to act,
	// draws the screen, waits a bit, and repeats.
	public void loop() throws IOException {
		gameTime = System.currentTimeMillis();
		int frames = 0;
		double unprocessedSeconds = 0;
		long previousTime = System.nanoTime();
		double secondsPerTick = 1 / 60.0;
		int tickCount = 0;
		boolean ticked = false;
		while (true) {
			if (Reset) {
				loadLevel();
				FlagSound = false;
				StageCleared = false;
			}

			// This is to check to see if the flag at the end of the level has
			// been reached
			for (ScreenObject so : background) {
				if (so instanceof Flag) {
					if (so.getX() < GAME_WIDTH - (CUBE_WIDTH * 12)) {
						music.stop();
						if (!FlagSound) {
							playSound(FlagPole);
							FlagSound = true;
						}

						if (!StageCleared) {
							playSound(StageClear);
							StageCleared = true;
						}

						right = false;

						if (LVL1_1) {
							LVL1_1 = false;
							LVL1_2 = true;
						} else if (LVL1_3) {
							LVL1_3 = false;
							LVL1_4 = true;
						}
						musicStarted = false;
						Reset = true;

						try {
							Thread.sleep(7000);
						} catch (Exception e) {
						}
					}
				}
			}

			if (!musicStarted && (LVL1_1 || LVL1_3)) {
				playMusic(MainTheme);
				musicStarted = true;
			}

			if (!musicStarted && (LVL1_2 || Bonus1_1 || Bonus1_2)) {
				playMusic(UnderWorld);
				musicStarted = true;
			}

			if (!musicStarted && LVL1_4) {
				playMusic(CastleTheme);
				musicStarted = true;
			}

			// Of course, the only objects I added are a brick and ground panel,
			// so act doesn't actually do anything. I just put it here so you
			// can see the structure of the game logic.
			for (ScreenObject so : objects) {
				if (this.getY() > GAME_HEIGHT + CUBE_HEIGHT) {
					objects.remove(so);
					continue;
				}
				if (so.getX() >= -300 && so.getX() <= GAME_WIDTH + 160) {
					so.act();
				}
			}

			int speed = 3;
			int bSpeed = 4;
			if (right && !rightCollision(objects, Mario)) {
				if (Mario.getX() < GAME_WIDTH / 2 && !LVL1_4) {
					Mario.setX(Mario.getX() + speed);
				} else {
					for (ScreenObject so : objects) {
						if (speedBoost) {
							so.setX(so.getX() - bSpeed);
						}

						so.setX(so.getX() - speed);
					}

					for (ScreenObject bg : background) {
						if (speedBoost)
							bg.setX(bg.getX() - bSpeed);

						bg.setX(bg.getX() - speed);
					}
				}
			} // end of right collision check

			/*
			 * This first if checks to see if Mario is at the beginning of the
			 * level. If he is then mario's position changes rather than the
			 * background objects. The else condition moves the objects past
			 * Mario while he stays in the center.
			 */
			if (left && objects.get(0).getX() > 0) {
				if (Mario.getX() > 50)
					Mario.setX(Mario.getX() - speed);

			} else if (left && !leftCollision(objects, Mario)) {
				for (ScreenObject so : objects) {
					if (speedBoost) {
						so.setX(so.getX() + bSpeed);
					}
					so.setX(so.getX() + speed);
				}

				/*
				 * Background objects consist of hills, clouds, and bushes.
				 * These objects are in there own list to prevent other sprites
				 * such as Goombas and Koopas from walking behind them. Also
				 * this ensures that clouds are behind bricks and block.
				 */
				for (ScreenObject bg : background) {
					if (speedBoost)
						bg.setX(bg.getX() + bSpeed);

					bg.setX(bg.getX() + speed);
				}
			}

			if (down && !floorCollision(objects, Mario)) {
				Mario.setY(Mario.getY() + 7);
			}

			if (JumpCheck >= JumpMax) {
				jump = false;
			}
			if (jump) {
				JumpCheck += 10;
				Mario.setY(Mario.getY() - 10);
			} else if (JumpCheck > 0) {
				JumpCheck -= 10;
				down = true;
			} else {
				JumpCheck = 0;
			}

			if (ceilingDetection(objects, Mario)) {
				jump = false;
				down = true;
			}

			if (Mario.getY() > GAME_HEIGHT) {
				Mario_Madness.music.stop();
				playSound(MarioDies);
				musicStarted = false;

				// endScreen(); //end screen to show data
				// endScreen2();

				try {
					Thread.sleep(3000);
				} catch (Exception e) {
				}
				Reset = true;
				timer = 0;
				kills = 0;
			}

			long currentTime = System.nanoTime();
			long passedTime = currentTime - previousTime;
			previousTime = currentTime;
			unprocessedSeconds += passedTime / 1000000000.0;

			while (unprocessedSeconds > secondsPerTick) {
				unprocessedSeconds -= secondsPerTick;
				ticked = true;
				tickCount++;
				if (tickCount % 60 == 0) {
					System.out.println(frames + "fps");
					previousTime += 1000;
					frames = 0;
				}
			}
			if (ticked) {
				frames++;
			}

			if (animationTime < 20) {
				animationTime++;
			} else {
				animationTime = 0;
			}

			frames++;
			Mario.playerAct(objects);
			draw();
		} // end of while loop

		// ********************
	}// end of loop method
	// ********************

	public void draw() {
		timer = (System.currentTimeMillis() - startTime) / 1000;

		while (StartScreen) {
			g2d.setColor(Color.BLACK);
			g2d.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);

			BufferedImage image = null;
			try {
				image = ImageIO.read(new File("res/Mario Textures/Start Screen.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			g2d.drawImage(image, 10, 10, null);

			Font instructions = new Font("TimesRoman", Font.BOLD, 25);
			g2d.setFont(instructions);
			g2d.setColor(Color.WHITE);
			g2d.drawString("Use the UP, DOWN, LEFT and RIGHT arrows", 270, 420);
			g2d.drawString("or the W, A, S and D keys to move Mario.", 305, 450);
			g2d.drawString("Press the SPACEBAR to Jump.", 350, 480);
			g2d.drawString("Press ENTER to start.", 400, 510);

			getGraphics().drawImage(raster, 0, 0, getWidth(), getHeight(), null);
		} // end of start screen while loop

		// First, I draw the background.
		if (LVL1_1 || LVL1_3) {
			defaultColor = (new Color(92, 148, 252));
			if (color == null) {
				color = defaultColor;
			}
			g2d.setColor(color);

		} else if (LVL1_2 || LVL1_4 || Bonus1_1 || Bonus1_2) {
			defaultColor = (Color.BLACK);
			if (color == null)
				color = defaultColor;
			g2d.setColor(defaultColor);
		}
		g2d.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		// Then I get the image of each object, and draw it at the object's
		// position. THis allows objects to move if their x or y change.
		for (ScreenObject bg : background) {
			if (bg.getX() >= -300 && bg.getX() <= GAME_WIDTH + 160) {
				g2d.drawImage(bg.getImage(), bg.getX(), bg.getY(), null);
			}
		}

		for (ScreenObject so : objects) {
			if (so.getX() >= -300 && so.getX() <= GAME_WIDTH + 160) {
				g2d.drawImage(so.getImage(), so.getX(), so.getY(), null);
			}
		}

		if (LVL1_4) {
			for (ScreenObject so : objects) {
				if (so instanceof ProfGanLaugh) {
					if (so.getX() < GAME_WIDTH + 200) {
						Font instructions = new Font("TimesRoman", Font.BOLD, 35);
						g2d.setFont(instructions);
						g2d.setColor(Color.WHITE);
						g2d.drawString("PROFESSOR GANLAUGH", so.getX() - 200, 200);
					}
				}
			}
		}

		// This last line is VERY important. You'll notice I'm not using g2d,
		// I'm using getGraphics to draw this last image. THat's because this
		// final line tells the frame to draw our raster image to the screen,
		// at 0,0. This creates "double buffering," meaning that we drew
		// everything
		// in the background and THEN threw it on the screen, rather than
		// drawing
		// straight to the screen every time. If we drew to the screen every
		// time,
		// it would create a flickering and ghosting effect and would look
		// terrible. You'll notice this takes 6 parameters. THe first one is the
		// image we want to draw, the first two ints are where to draw it, the
		// second two are how large to scale it, and the last is always null.
		// The last two are getWidth and getHeight- this is how the frame scales
		// the game window to the frame size.Whatever the current size of the
		// frame is, that's what the game window will be scaled to, and it
		// creates
		// a nice resizeable game screen.

		g2d.drawImage(Mario.getImage(), Mario.getX(), Mario.getY(), null);
		getGraphics().drawImage(raster, 0, 0, getWidth(), getHeight(), null);

		// ********************
	}// end of draw method
	// ********************

	public void endScreen() // ENDSCREEN*******************************************************
	{
		BufferedImage raster = new BufferedImage(GAME_WIDTH, GAME_HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g2d2 = raster.createGraphics();
		g2d2.setColor(Color.black);
		g2d2.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		g2d2.setColor(Color.yellow);
		g2d2.setFont(new Font("TimesRoman", Font.BOLD, 35));
		g2d2.drawString("Time: " + timer + " sec", (GAME_WIDTH / 2) - 100, (GAME_HEIGHT / 2) - 120);
		g2d2.drawString("Coins: " + coins, (GAME_WIDTH / 2) - 100, (GAME_HEIGHT / 2) - 75);
		g2d2.drawString("Kills: " + kills, (GAME_WIDTH / 2) - 100, (GAME_HEIGHT / 2) - 30);
		g2d2.drawString("Score: " + score, (GAME_WIDTH / 2) - 100, (GAME_HEIGHT / 2) + 15);

		getGraphics().drawImage(raster, 0, 0, getWidth(), getHeight(), null);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void endScreen2()// 2nd endgame screen to show bar graph
	{
		BufferedImage raster = new BufferedImage(GAME_WIDTH, GAME_HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g2d3 = raster.createGraphics();
		g2d3.setColor(Color.black);
		g2d3.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		g2d3.setColor(Color.yellow);
		g2d3.setFont(new Font("TimesRoman", Font.BOLD, 35));
		g2d3.drawString("Time", GAME_WIDTH - 850, GAME_HEIGHT - 30);
		g2d3.drawString("Coins", GAME_WIDTH - 650, GAME_HEIGHT - 30);
		g2d3.drawString("Kills", GAME_WIDTH - 450, GAME_HEIGHT - 30);
		g2d3.drawString("Score", GAME_WIDTH - 250, GAME_HEIGHT - 30);
		g2d3.setColor(Color.GREEN);
		g2d3.fillRect(GAME_WIDTH - 850, GAME_HEIGHT - 70 - (int) (timer * 2), 150, (int) (timer * 2));
		g2d3.setColor(Color.CYAN);
		g2d3.fillRect(GAME_WIDTH - 650, GAME_HEIGHT - 70 - (int) (coins * 5), 150, (int) (coins * 5));
		g2d3.setColor(Color.RED);
		g2d3.fillRect(GAME_WIDTH - 450, GAME_HEIGHT - 70 - (int) (kills * 10), 150, (int) (kills * 10));
		g2d3.setColor(Color.MAGENTA);
		g2d3.fillRect(GAME_WIDTH - 250, GAME_HEIGHT - 70 - (int) (score / 50), 150, (int) (score / 50));
		getGraphics().drawImage(raster, 0, 0, getWidth(), getHeight(), null);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
			right = true;
		}

		if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT)
			left = true;

		if (e.getKeyCode() == KeyEvent.VK_SPACE) {

			if (isOnGround) {
				if (!jumpSoundPlayed)
					Mario_Madness.playSound(SmallJump);
				isOnGround = false;
				jump = true;
				pressedJump = true;
				down = false;
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_B)
			speedBoost = true;

		if (e.getKeyCode() == KeyEvent.VK_ENTER)
			StartScreen = false;

		if (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN)
			duck = true;

		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			color = JColorChooser.showDialog(null, "Pick your color", color);
			if (color == null)
				color = defaultColor;
		}

		if (e.getKeyCode() == KeyEvent.VK_1) {
			LVL1_1 = true;
			LVL1_2 = false;
			LVL1_3 = false;
			LVL1_4 = false;
			Bonus1_1 = false;
			Bonus1_2 = false;
			Reset = true;
			music.stop();
			musicStarted = false;
		}

		if (e.getKeyCode() == KeyEvent.VK_2) {
			LVL1_2 = true;
			LVL1_1 = false;
			LVL1_3 = false;
			LVL1_4 = false;
			Bonus1_1 = false;
			Bonus1_2 = false;
			Reset = true;
			music.stop();
			musicStarted = false;
		}

		if (e.getKeyCode() == KeyEvent.VK_3) {
			LVL1_3 = true;
			LVL1_1 = false;
			LVL1_2 = false;
			LVL1_4 = false;
			Bonus1_1 = false;
			Bonus1_2 = false;
			Reset = true;
			music.stop();
			musicStarted = false;
		}

		if (e.getKeyCode() == KeyEvent.VK_4) {
			LVL1_4 = true;
			LVL1_1 = false;
			LVL1_2 = false;
			LVL1_3 = false;
			Bonus1_1 = false;
			Bonus1_2 = false;
			Reset = true;
			music.stop();
			musicStarted = false;
		}

		if (e.getKeyCode() == KeyEvent.VK_5) {
			Bonus1_1 = true;
			LVL1_1 = false;
			LVL1_2 = false;
			LVL1_3 = false;
			LVL1_4 = false;
			Bonus1_2 = false;
			Reset = true;
			music.stop();
			musicStarted = false;
		}

		if (e.getKeyCode() == KeyEvent.VK_6) {
			Bonus1_2 = true;
			LVL1_1 = false;
			LVL1_2 = false;
			LVL1_3 = false;
			LVL1_4 = false;
			Bonus1_1 = false;
			Reset = true;
			music.stop();
			musicStarted = false;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT)
			right = false;

		if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT)
			left = false;

		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			pressedJump = false;
			down = true;
		}

		if (e.getKeyCode() == KeyEvent.VK_B)
			speedBoost = false;

		if (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN)
			duck = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	// the method flips the image horizontally
	public static BufferedImage transformImage(BufferedImage image) {
		AffineTransform transform = AffineTransform.getScaleInstance(-1, 1);
		transform.translate(-image.getWidth(null), 0);
		AffineTransformOp transformOp = new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		return transformOp.filter(image, null);
	}
}// end of Mario_Madness class

abstract class Actor {
	int x;
	int y;
	BufferedImage image;
	boolean big = false;
	boolean powerUp = false;

	public Actor(int x, int y, String imageFileName) throws IOException {
		this.x = x;
		this.y = y;
		image = ImageIO.read(new File(imageFileName));
	}

	public abstract void playerAct(ArrayList<ScreenObject> so);

	public abstract void powerUpAnimation();

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

}// end of Actor class

class Mario extends Actor {
	ArrayList<BufferedImage> grow = new ArrayList<>();
	BufferedImage mushroomAnimation[] = new BufferedImage[10];
	BufferedImage jumpImage[] = new BufferedImage[2];
	BufferedImage walking[] = new BufferedImage[16];
	int mushroomAnimationCounter = 0;
	int walkAnimationCounter = 0;

	public Mario(int x, int y) throws IOException {
		super(x, y, "res/Mario Textures/Small Mario Right.png");
		grow.add(this.getImage());
		grow.add(ImageIO.read(new File("res/Mario Textures/MediumMario.png")));
		grow.add(ImageIO.read(new File("res/Mario Textures/BigMario001.png")));
		mushroomAnimation[0] = grow.get(0);// small
		mushroomAnimation[1] = grow.get(1);// medium
		mushroomAnimation[2] = grow.get(0);// small
		mushroomAnimation[3] = grow.get(1);// medium
		mushroomAnimation[4] = grow.get(0);// small
		mushroomAnimation[5] = grow.get(1);// medium
		mushroomAnimation[6] = grow.get(2);// large
		mushroomAnimation[7] = grow.get(0);// small
		mushroomAnimation[8] = grow.get(1);// medium
		mushroomAnimation[9] = grow.get(2);// large

		jumpImage[0] = ImageIO.read(new File("res/Mario Textures/SmallJump.png"));
		jumpImage[1] = ImageIO.read(new File("res/Mario Textures/BigJump.png"));

		// Big Mario walk right
		walking[0] = ImageIO.read(new File("res/Mario Textures/BigWalking00.png"));
		walking[1] = ImageIO.read(new File("res/Mario Textures/BigWalking01.png"));
		walking[2] = ImageIO.read(new File("res/Mario Textures/BigWalking02.png"));
		walking[3] = Mario_Madness.transformImage(ImageIO.read(new File("res/Mario Textures/BigWalking03.png")));
		// Big Mario walk left
		walking[4] = Mario_Madness.transformImage(ImageIO.read(new File("res/Mario Textures/BigWalking00.png")));
		walking[5] = Mario_Madness.transformImage(ImageIO.read(new File("res/Mario Textures/BigWalking01.png")));
		walking[6] = Mario_Madness.transformImage(ImageIO.read(new File("res/Mario Textures/BigWalking02.png")));
		walking[7] = ImageIO.read(new File("res/Mario Textures/BigWalking03.png"));
		// Small Mario walk right
		walking[8] = ImageIO.read(new File("res/Mario Textures/SmallWalking00.png"));
		walking[9] = ImageIO.read(new File("res/Mario Textures/SmallWalking01.png"));
		walking[10] = ImageIO.read(new File("res/Mario Textures/SmallWalking02.png"));
		walking[11] = Mario_Madness.transformImage(ImageIO.read(new File("res/Mario Textures/SmallWalking03.png")));
		// Small Mario walk left
		walking[12] = Mario_Madness.transformImage(ImageIO.read(new File("res/Mario Textures/SmallWalking00.png")));
		walking[13] = Mario_Madness.transformImage(ImageIO.read(new File("res/Mario Textures/SmallWalking01.png")));
		walking[14] = Mario_Madness.transformImage(ImageIO.read(new File("res/Mario Textures/SmallWalking02.png")));
		walking[15] = ImageIO.read(new File("res/Mario Textures/SmallWalking03.png"));
	}// end of constructor

	@Override
	public void playerAct(ArrayList<ScreenObject> objects) {
//		this.setY(this.getY() - (int) Math.sin(Mario_Madness.animationTime) * image.getHeight());

		// System.out.println(this.image.getSubimage(x + image.getWidth(), y +
		// image.getHeight(), image.getWidth(), image.getHeight()).toString());

		if (Mario_Madness.right) {
			if (big) {
				if (walkAnimationCounter > 2) {
					walkAnimationCounter = 0;
				}
				if (Mario_Madness.animationTime == 0) {
					image = walking[walkAnimationCounter++];
				}
			} else {
				// small
				if (walkAnimationCounter < 8 || walkAnimationCounter > 10) {
					walkAnimationCounter = 8;
				}
				if (Mario_Madness.animationTime == 0) {
					image = walking[walkAnimationCounter++];
				}
			}
			return;
		} else if (Mario_Madness.left) {
			if (big) {
				if (walkAnimationCounter < 4 || walkAnimationCounter > 6) {
					walkAnimationCounter = 4;
				}
				if (Mario_Madness.animationTime == 0) {
					image = walking[walkAnimationCounter++];
				}

			} else {
				// small
				if (walkAnimationCounter < 12 || walkAnimationCounter > 14) {
					walkAnimationCounter = 12;
				}
				if (Mario_Madness.animationTime == 0) {
					image = walking[walkAnimationCounter++];
				}
			}
			return;
		} else {// release left button
			if (big) {
				image = mushroomAnimation[9];
			} else {
				image = mushroomAnimation[0];
			}
		}

		if (Mario_Madness.jump) {
			if (big) {
				image = jumpImage[1];
				return;
			} else {
				image = jumpImage[0];
				return;
			}
		} else {
			if (big) {
				image = mushroomAnimation[9];
			} else {
				image = mushroomAnimation[0];
			}
		}
		// grow animation when small mario gets a mushroom
		if (big) {
			if (Mario_Madness.animationTime == 0) {
				if (mushroomAnimationCounter < 10) {
					this.setImage(mushroomAnimation[mushroomAnimationCounter++]);
					if (mushroomAnimationCounter == 1 || mushroomAnimationCounter == 3 || mushroomAnimationCounter == 5
							|| mushroomAnimationCounter == 8) {
						y -= 20;
					} else if (mushroomAnimationCounter == 6 || mushroomAnimationCounter == 9) {
						y -= 40;
					}
				}
				return;
			}
		}
	}// end of player act method

	public void powerUpAnimation() {
		if (!big) {
			big = true;
		}
	}// end of mushroomAnimation method

}// end of Mario class

// This class is the overall class for every object that can be drawn to the
// screen. It could also be an interface if you wanted. All it can do is
// keep track of its own X and Y coordinate, and remember what its picture is.
// If any drawable object can't find its picture file, it will throw an error.
abstract class ScreenObject {
	public boolean bumped;
	int x;
	int y;
	int hits = 0;
	int MaxHits = 5;
	boolean solid, breakable, spawned;
	BufferedImage image;

	public ScreenObject(int x, int y, String imageFileName) {
		this.x = x;
		this.y = y;
		try {
			image = ImageIO.read(new File(imageFileName));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Could not load " + this.getClass() + " image");
		}
	}

	public abstract void act();

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public BufferedImage getImage() {
		return image;
	}
}

// A Brick is a type of screenobject that can be placed at any x and y, and
// has an image of Brick.png. It cannot act.
class Brick extends ScreenObject {
	int hits = 0;
	int MaxHits = 5;
	boolean moved = false;
	boolean returned = true;
	int start = 0;
	int end = 15;

	public Brick(int x, int y) {
		super(x, y, "res/Mario Textures/Brick.png");
	}

	public Brick(int x, int y, String s) {
		super(x, y, s);
	}

	@Override
	public void act() {
		if (bumped) {
			if (!moved) {
				this.setY(this.getY() - 5);
				moved = true;
			}
			start += 5;

			if (start > end) {
				returned = false;
			}
			if (!returned) {
				this.setY(this.getY() + 5);
				returned = true;
			}
			if (start > end && returned) {
				bumped = false;
				moved = false;
				start = 0;
			}
		}
	}
}

class BlueBrick extends Brick {
	boolean breakable = true;

	public BlueBrick(int x, int y) {
		super(x, y, "res/Mario Textures/Blue Brick.png");
	}

	@Override
	public void act() {
		if (bumped) {
			if (!moved) {
				this.setY(this.getY() - 5);
				moved = true;
			}
			start += 5;

			if (start > end) {
				returned = false;
			}
			if (!returned) {
				this.setY(this.getY() + 5);
				returned = true;
			}
			if (start > end && returned) {
				bumped = false;
				moved = false;
				start = 0;
			}
		}
	}
}

class CoinBrick extends Brick {
	int hits = 0;
	int MaxHits = 5;
	boolean breakable = true;

	public CoinBrick(int x, int y) {
		super(x, y, "res/Mario Textures/Brick.png");
	}

	@Override
	public void act() {
		if (bumped) {
			if (!moved) {
				this.setY(this.getY() - 5);
				moved = true;
			}
			start += 5;

			if (start > end) {
				returned = false;
			}
			if (!returned) {
				this.setY(this.getY() + 5);
				returned = true;
			}
			if (start > end && returned) {
				bumped = false;
				moved = false;
				start = 0;
			}
		}
	}
}

class BlueCoinBrick extends Brick {
	boolean breakable = true;

	public BlueCoinBrick(int x, int y) {
		super(x, y, "res/Mario Textures/Blue Brick.png");
	}

	@Override
	public void act() {
		if (bumped) {
			if (!moved) {
				this.setY(this.getY() - 5);
				moved = true;
			}
			start += 5;

			if (start > end) {
				returned = false;
			}
			if (!returned) {
				this.setY(this.getY() + 5);
				returned = true;
			}
			if (start > end && returned) {
				bumped = false;
				moved = false;
				start = 0;
			}
		}
	}
}

class GrayBrick extends ScreenObject {
	boolean breakable = true;

	public GrayBrick(int x, int y) {
		super(x, y, "res/Mario Textures/Gray Brick.png");
	}

	@Override
	public void act() {
	}
}

// Ground is the same as Brick, but with a different picture.
class Ground extends ScreenObject {
	boolean solid = true;

	public Ground(int x, int y) {
		super(x, y, "res/Mario Textures/Ground.png");
	}

	@Override
	public void act() {
	}
}

class BlueGround extends ScreenObject {
	boolean solid = true;

	public BlueGround(int x, int y) {
		super(x, y, "res/Mario Textures/Blue Ground.png");
	}

	@Override
	public void act() {
	}
}

class CoinBlock extends ScreenObject {
	boolean solid = true;

	public CoinBlock(int x, int y) {
		super(x, y, "res/Mario Textures/Coin Block.png");
	}

	@Override
	public void act() {
	}
}

class Steel extends ScreenObject {

	public Steel(int x, int y) {
		super(x, y, "res/Mario Textures/Steel.png");
	}

	@Override
	public void act() {
	}
}

class BlueSteel extends ScreenObject {
	boolean solid = true;

	public BlueSteel(int x, int y) {
		super(x, y, "res/Mario Textures/Blue Steel.png");
	}

	@Override
	public void act() {
	}
}

class Cloud extends ScreenObject {
	public Cloud(int x, int y) {
		super(x, y, "res/Mario Textures/Cloud.png");
	}

	@Override
	public void act() {
	}
}

class Goomba extends ScreenObject {
	public int changeDir = -1;
	public int speed = -1;
	private BufferedImage animation[];

	public Goomba(int x, int y) {
		super(x, y, "res/Mario Textures/Goomba.png");
		animation = new BufferedImage[2];
		animation[0] = this.image;
		animation[1] = Mario_Madness.transformImage(this.image);
	}

	public Goomba(int x, int y, String imageFileName) {
		super(x, y, imageFileName);
	}

	@Override
	public void act() {
		if (Mario_Madness.animationTime > 10) {
			image = animation[0];
		} else {
			image = animation[1];
		}

		if (!floorCollision(Mario_Madness.objects, this))
			this.setY(this.getY() + 5);

		if (leftCollision(Mario_Madness.objects, this))
			speed *= changeDir;

		if (rightCollision(Mario_Madness.objects, this))
			speed *= changeDir;

		this.setX(this.getX() + speed);
	}

	protected boolean rightCollision(ArrayList<ScreenObject> o, Goomba m) {
		for (ScreenObject so : o) {
			if (so instanceof Ground || so instanceof BlueGround || so instanceof BlueBlock || so instanceof Block
					|| so instanceof CoinBlock || so instanceof Brick || so instanceof BlueBrick
					|| so instanceof Goomba) {
				if (m.getY() + Mario_Madness.CUBE_HEIGHT > so.getY()
						&& m.getY() < so.getY() + Mario_Madness.CUBE_HEIGHT) {
					if (m.getX() + Mario_Madness.CUBE_WIDTH > so.getX()
							&& m.getX() + Mario_Madness.CUBE_WIDTH < so.getX() + Mario_Madness.CUBE_WIDTH) {
						return true;
					}
				}
			} else if (so instanceof Pipe || so instanceof BonusEntrance || so instanceof BonusExit) {
				if (m.getY() + Mario_Madness.CUBE_HEIGHT > so.getY()
						&& m.getY() < so.getY() + Mario_Madness.CUBE_HEIGHT * 4) {
					if ((m.getX() + Mario_Madness.CUBE_WIDTH > (so.getX()))
							&& (m.getX() + Mario_Madness.CUBE_WIDTH < so.getX() + Mario_Madness.CUBE_WIDTH / 2)) {
						return true;
					}
				}
			} else if (so instanceof UpElevator || so instanceof DownElevator || so instanceof Elevator) {
				if (m.getY() + Mario_Madness.CUBE_HEIGHT > so.getY()
						&& m.getY() < so.getY() + Mario_Madness.CUBE_HEIGHT) {
					if ((m.getX() + Mario_Madness.CUBE_WIDTH > (so.getX() - 3))
							&& (m.getX() + Mario_Madness.CUBE_WIDTH < so.getX() + Mario_Madness.CUBE_WIDTH)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	protected boolean leftCollision(ArrayList<ScreenObject> o, Goomba m) {
		for (ScreenObject so : o) {
			if (so instanceof Ground || so instanceof BlueGround || so instanceof BlueBlock || so instanceof Block
					|| so instanceof CoinBlock || so instanceof Brick || so instanceof BlueBrick
					|| so instanceof Goomba) {
				if (m.getY() + Mario_Madness.CUBE_HEIGHT > so.getY()
						&& m.getY() < so.getY() + Mario_Madness.CUBE_HEIGHT) {
					if (m.getX() < so.getX() + Mario_Madness.CUBE_WIDTH && m.getX() > so.getX()) {
						return true;
					}
				}
			} else if (so instanceof Pipe || so instanceof BonusEntrance || so instanceof BonusExit) {
				if (m.getY() + Mario_Madness.CUBE_HEIGHT > so.getY()
						&& m.getY() < so.getY() + Mario_Madness.CUBE_HEIGHT * 4) {
					if ((m.getX() < (so.getX() + Mario_Madness.CUBE_WIDTH * 2))
							&& (m.getX() > so.getX() + Mario_Madness.CUBE_WIDTH)) {
						return true;
					}
				}
			} else if (so instanceof UpElevator || so instanceof DownElevator || so instanceof Elevator) {
				if (m.getY() + Mario_Madness.CUBE_HEIGHT > so.getY()
						&& m.getY() < so.getY() + Mario_Madness.CUBE_HEIGHT) {
					if ((m.getX() < (so.getX() + Mario_Madness.CUBE_WIDTH * 3))
							&& (m.getX() > so.getX() + Mario_Madness.CUBE_WIDTH)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	protected boolean floorCollision(ArrayList<ScreenObject> o, Goomba m) {
		for (ScreenObject so : o)
			if (so instanceof Ground || so instanceof BlueGround || so instanceof BlueBlock || so instanceof Block
					|| so instanceof CoinBlock || so instanceof Brick || so instanceof BlueBrick
					|| so instanceof TreeTopLeft || so instanceof TreeTop || so instanceof TreeTopRight
					|| so instanceof Goomba || so instanceof Steel || so instanceof BlueSteel) {
				if (m.getX() + Mario_Madness.CUBE_WIDTH > so.getX()
						&& m.getX() < so.getX() + Mario_Madness.CUBE_WIDTH) {
					if (m.getY() + Mario_Madness.CUBE_HEIGHT >= so.getY()
							&& m.getY() + Mario_Madness.CUBE_HEIGHT < so.getY() + Mario_Madness.CUBE_HEIGHT) {
						m.setY(so.getY() - Mario_Madness.CUBE_HEIGHT);
						return true;
					}
				}
			} else if (so instanceof Pipe) {
				if (m.getX() + Mario_Madness.CUBE_WIDTH > so.getX()
						&& m.getX() < so.getX() + Mario_Madness.CUBE_WIDTH * 2) {
					if (m.getY() + Mario_Madness.CUBE_HEIGHT < so.getY()
							&& m.getY() + Mario_Madness.CUBE_HEIGHT < so.getY() + Mario_Madness.CUBE_HEIGHT) {
						m.setY(so.getY() - Mario_Madness.CUBE_HEIGHT);
						return true;
					}
				}
			} else if (so instanceof UpElevator || so instanceof DownElevator || so instanceof Elevator) {
				if (m.getX() + Mario_Madness.CUBE_WIDTH > so.getX()
						&& m.getX() < so.getX() + Mario_Madness.CUBE_WIDTH * 4) {
					if (m.getY() + Mario_Madness.CUBE_HEIGHT >= so.getY()
							&& m.getY() + Mario_Madness.CUBE_HEIGHT <= so.getY() + Mario_Madness.CUBE_HEIGHT / 2) {
						m.setY(so.getY() - Mario_Madness.CUBE_HEIGHT);
						return true;
					}
				}
			}
		return false;
	}
}

class BlueGoomba extends Goomba {
	private int changeDir = -1;
	private int speed = -2;

	public BlueGoomba(int x, int y) {
		super(x, y, "res/Mario Textures/Blue Goomba.png");
	}

	@Override
	public void act() {
		if (!floorCollision(Mario_Madness.objects, this))
			this.setY(this.getY() + 5);

		if (leftCollision(Mario_Madness.objects, this))
			speed *= changeDir;

		if (rightCollision(Mario_Madness.objects, this))
			speed *= changeDir;

		this.setX(this.getX() + speed);
	}
}

class Hill extends ScreenObject {
	public Hill(int x, int y) {
		super(x, y, "res/Mario Textures/Hill.png");
	}

	@Override
	public void act() {
	}
}

class Bush extends ScreenObject {
	public Bush(int x, int y) {
		super(x, y, "res/Mario Textures/Bush.png");
	}

	@Override
	public void act() {
	}
}

class Block extends ScreenObject {
	boolean solid = true;

	public Block(int x, int y) {
		super(x, y, "res/Mario Textures/Block.png");
	}

	@Override
	public void act() {
	}
}

class BlueBlock extends ScreenObject {
	boolean solid = true;

	public BlueBlock(int x, int y) {
		super(x, y, "res/Mario Textures/Blue Block.png");
	}

	@Override
	public void act() {
	}
}

class Pipe extends ScreenObject {
	boolean solid = true;

	public Pipe(int x, int y) {
		super(x, y, "res/Mario Textures/Pipe.png");
	}

	@Override
	public void act() {
	}
}

class LongPipe extends ScreenObject {
	boolean solid = true;

	public LongPipe(int x, int y) {
		super(x, y, "res/Mario Textures/Long Pipe.png");
	}

	@Override
	public void act() {
	}
}

class SidePipe extends ScreenObject {
	boolean solid = true;

	public SidePipe(int x, int y) {
		super(x, y, "res/Mario Textures/Side Pipe.png");
	}

	@Override
	public void act() {
	}
}

class Castle extends ScreenObject {
	public Castle(int x, int y) {
		super(x, y, "res/Mario Textures/Castle.png");
	}

	@Override
	public void act() {
	}
}

class Flag extends ScreenObject {
	public Flag(int x, int y) {
		super(x, y, "res/Mario Textures/Flag.png");
	}

	@Override
	public void act() {
	}
}

class Coin extends ScreenObject {
	public Coin(int x, int y) {
		super(x, y, "res/Mario Textures/Coin.png");
	}

	@Override
	public void act() {
	}
}

class BlueCoin extends ScreenObject {
	public BlueCoin(int x, int y) {
		super(x, y, "res/Mario Textures/Blue Coin.png");
	}

	@Override
	public void act() {
	}
}

class Elevator extends ScreenObject {
	int changeDir = -1;
	int speed = 3;
	int start = 0;
	int end = 400;

	public Elevator(int x, int y) {
		super(x, y, "res/Mario Textures/Elevator.png");
	}

	public Elevator(int x, int y, String s) {
		super(x, y, s);
	}

	@Override
	public void act() {
	}
}

// Up in LVL2 and up/down in LVL3
class UpElevator extends Elevator {
	int changeDir = -1;
	int speed = 3;
	int start = 0;
	int end = 400;

	public UpElevator(int x, int y) {
		super(x, y, "res/Mario Textures/Elevator.png");
	}

	@Override
	public void act() {

		if (Mario_Madness.LVL1_2) {
			if (this.getY() < 0)
				this.setY(Mario_Madness.GAME_HEIGHT);

			this.setY(this.getY() - speed);
		}

		if (Mario_Madness.LVL1_3) {
			if (start < 0)
				speed *= changeDir;

			if (start >= end)
				speed *= changeDir;

			start += speed;
			this.setY(this.getY() - speed);
		}
	}
}

// Down in LVL2 and side to side in LVL3
class DownElevator extends Elevator {
	int changeDir = -1;
	int speed = 2;
	int start = 0;
	int end = 200;

	public DownElevator(int x, int y) {
		super(x, y, "res/Mario Textures/Elevator.png");
	}

	@Override
	public void act() {

		if (Mario_Madness.LVL1_2) {
			if (this.getY() > Mario_Madness.GAME_HEIGHT)
				this.setY(0);

			this.setY(this.getY() + speed);
		}

		if (Mario_Madness.LVL1_3) {
			if (start < 0)
				speed *= changeDir;

			if (start > end) {
				speed *= changeDir;
			}

			start += speed;
			this.setX(this.getX() + speed);
		}
	}
}

class Koopa extends Goomba {
	public Koopa(int x, int y) {
		super(x, y, "res/Mario Textures/Koopa.png");
	}

	public Koopa(int x, int y, String imageFileName) {
		super(x, y, imageFileName);
	}

	@Override
	public void act() {

		if (!floorCollision(Mario_Madness.objects, this))
			this.setY(this.getY() + 5);

		if (leftCollision(Mario_Madness.objects, this))
			speed *= changeDir;

		if (rightCollision(Mario_Madness.objects, this))
			speed *= changeDir;

		this.setX(this.getX() + speed);

	}

	protected boolean floorCollision(ArrayList<ScreenObject> o, Koopa m) {
		for (ScreenObject so : o)
			if (so instanceof Ground || so instanceof BlueGround || so instanceof BlueBlock || so instanceof Block
					|| so instanceof CoinBlock || so instanceof Brick || so instanceof BlueBrick
					|| so instanceof TreeTopLeft || so instanceof TreeTop || so instanceof TreeTopRight) {
				if (m.getX() + Mario_Madness.CUBE_WIDTH > so.getX()
						&& m.getX() < so.getX() + Mario_Madness.CUBE_WIDTH) {
					if (m.getY() + Mario_Madness.CUBE_HEIGHT * 2 >= so.getY()
							&& m.getY() + Mario_Madness.CUBE_HEIGHT * 2 < so.getY() + Mario_Madness.CUBE_HEIGHT) {
						m.setY(so.getY() - Mario_Madness.CUBE_HEIGHT * 2);
						return true;
					}
				}
			} else if (so instanceof Pipe) {
				if (m.getX() + Mario_Madness.CUBE_WIDTH > so.getX()
						&& m.getX() < so.getX() + Mario_Madness.CUBE_WIDTH * 2) {
					if (m.getY() + Mario_Madness.CUBE_HEIGHT * 2 < so.getY()
							&& m.getY() + Mario_Madness.CUBE_HEIGHT * 2 < so.getY() + Mario_Madness.CUBE_HEIGHT) {
						m.setY(so.getY() - Mario_Madness.CUBE_HEIGHT * 2);
						return true;
					}
				}
			} else if (so instanceof UpElevator || so instanceof DownElevator || so instanceof Elevator) {
				if (m.getX() + Mario_Madness.CUBE_WIDTH > so.getX()
						&& m.getX() < so.getX() + Mario_Madness.CUBE_WIDTH * 3) {
					if (m.getY() + Mario_Madness.CUBE_HEIGHT * 2 >= so.getY()
							&& m.getY() + Mario_Madness.CUBE_HEIGHT * 2 <= so.getY() + Mario_Madness.CUBE_HEIGHT / 2) {
						m.setY(so.getY() - Mario_Madness.CUBE_HEIGHT * 2);
						return true;
					}
				}
			}
		return false;
	}
}

class BlueKoopa extends Koopa {
	public BlueKoopa(int x, int y) {
		super(x, y, "res/Mario Textures/Blue Koopa.png");
	}

	@Override
	public void act() {

		if (!floorCollision(Mario_Madness.objects, this))
			this.setY(this.getY() + 5);

		if (leftCollision(Mario_Madness.objects, this))
			speed *= changeDir;

		if (rightCollision(Mario_Madness.objects, this))
			speed *= changeDir;

		this.setX(this.getX() + speed);

	}
}

class RedKoopa extends Koopa {
	public RedKoopa(int x, int y) {
		super(x, y, "res/Mario Textures/Red Koopa.png");
	}

	@Override
	public void act() {

		if (!floorCollision(Mario_Madness.objects, this))
			this.setY(this.getY() + 5);

		if (leftCollision(Mario_Madness.objects, this))
			speed *= changeDir;

		if (rightCollision(Mario_Madness.objects, this))
			speed *= changeDir;

		this.setX(this.getX() + speed);
	}
}

class Pirahna extends ScreenObject {
	public Pirahna(int x, int y) {
		super(x, y, "");
	}

	@Override
	public void act() {
	}
}

class BluePirahna extends ScreenObject {
	int height = 0;
	int peak = 80;
	int move = 2;
	int count = 0;

	boolean up = false;
	boolean down = true;

	public BluePirahna(int x, int y) {
		super(x, y, "res/Mario Textures/Blue Pirahna2.png");
	}

	@Override
	public void act() {
		if (down) {
			if (count == 0)
				this.setY(this.getY() + move);

			height += 2;
			if (height > peak) {
				height = 0;
				count++;
				if (count > 3) {
					down = false;
					up = true;
					count = 0;
				}
			}
		}

		if (up) {
			if (count == 0)
				this.setY(this.getY() - move);

			height += 2;
			if (height > peak) {
				height = 0;
				count++;
				if (count > 3) {
					down = true;
					up = false;
					count = 0;
				}
			}
		}
	}
}

class MagicMushroom extends Goomba {
	int start = 0;
	int end = 40;
	boolean isOut = false;

	public MagicMushroom(int x, int y) {
		super(x, y, "res/Mario Textures/Magic Mushroom.png");
	}

	@Override
	public void act() {

		if (this.getX() < Mario_Madness.GAME_WIDTH && Mario_Madness.mushroomSpawned) {
			if (start < end && !isOut) {
				this.setY(this.getY() - 2);
				start += 2;
			}
			if (start >= end) {
				start = 0;
				isOut = true;
			}
		}

		if (isOut) {
			if (!floorCollision(Mario_Madness.objects, this))
				this.setY(this.getY() + 5);

			if (leftCollision(Mario_Madness.objects, this))
				speed *= changeDir;

			if (rightCollision(Mario_Madness.objects, this))
				speed *= changeDir;

			this.setX(this.getX() - speed);
		}
	}

	private boolean ceilingDetection(Actor m, MagicMushroom so) {
		if (m.getX() + Mario_Madness.CUBE_WIDTH > so.getX() && m.getX() < so.getX() + Mario_Madness.CUBE_WIDTH) {
			if (m.getY() <= so.getY() + Mario_Madness.CUBE_HEIGHT
					&& m.getY() >= so.getY() + Mario_Madness.CUBE_HEIGHT) {
				return true;
			}
		}
		return false;
	}
}

class FireFlower extends ScreenObject {
	public FireFlower(int x, int y) {
		super(x, y, "res/Mario Textures/Fire Flower.png");
	}

	@Override
	public void act() {
	}
}

class Star extends ScreenObject {
	public Star(int x, int y) {
		super(x, y, "res/Mario Textures/Star.png");
	}

	@Override
	public void act() {
	}
}

class TreeTopLeft extends ScreenObject {
	public TreeTopLeft(int x, int y) {
		super(x, y, "res/Mario Textures/Tree Top Left.png");
	}

	@Override
	public void act() {
	}
}

class TreeTop extends ScreenObject {
	public TreeTop(int x, int y) {
		super(x, y, "res/Mario Textures/Tree Top.png");
	}

	@Override
	public void act() {
	}
}

class TreeTopRight extends ScreenObject {
	public TreeTopRight(int x, int y) {
		super(x, y, "res/Mario Textures/Tree Top Right.png");
	}

	@Override
	public void act() {
	}
}

class TreeTrunk extends ScreenObject {
	public TreeTrunk(int x, int y) {
		super(x, y, "res/Mario Textures/Tree Trunk.png");
	}

	@Override
	public void act() {
	}
}

class RedKoopaTroopa extends Koopa {
	public RedKoopaTroopa(int x, int y) {
		super(x, y, "res/Mario Textures/Red Koopa Troopa.png");
	}

	@Override
	public void act() {
	}
}

class ScreenStarter extends ScreenObject {
	public ScreenStarter(int x, int y) {
		super(x, y, "res/Mario Textures/Blank.png");
	}

	@Override
	public void act() {
	}
}

class PowerUpBlock extends ScreenObject {
	public PowerUpBlock(int x, int y) {
		super(x, y, "res/Mario Textures/Coin Block.png");
	}

	@Override
	public void act() {
	}
}

class StarBlock extends ScreenObject {
	public StarBlock(int x, int y) {
		super(x, y, "res/Mario Textures/Coin Block.png");
	}

	@Override
	public void act() {
	}
}

class BonusEntrance extends ScreenObject {
	public BonusEntrance(int x, int y) {
		super(x, y, "res/Mario Textures/Pipe.png");
	}

	@Override
	public void act() {
	}
}

class BonusExit extends ScreenObject {
	public BonusExit(int x, int y) {
		super(x, y, "res/Mario Textures/Pipe.png");
	}

	@Override
	public void act() {
	}
}

class LavaTop extends ScreenObject {
	public LavaTop(int x, int y) {
		super(x, y, "res/Mario Textures/Lava Top.png");
	}

	@Override
	public void act() {
	}
}

class LavaBottom extends ScreenObject {
	public LavaBottom(int x, int y) {
		super(x, y, "res/Mario Textures/Lava Bottom.png");
	}

	@Override
	public void act() {
	}
}

class Bridge extends ScreenObject {
	public Bridge(int x, int y) {
		super(x, y, "res/Mario Textures/Bridge.png");
	}

	@Override
	public void act() {
	}
}

class ProfGanLaugh extends ScreenObject {
	int Count = 0;
	boolean played = false;

	public ProfGanLaugh(int x, int y) {
		super(x, y, "res/Mario Textures/Professor GanLaugh.png");
	}

	@Override
	public void act() {
		if (this.getX() < Mario_Madness.GAME_WIDTH && Count == 0)
			Mario_Madness.playSound(Mario_Madness.ShallNotPass);

		Count++;
		if (Count > 500)
			Count = 0;
	}
}

class LavaFireBall extends Elevator {
	int changeDir = -1;
	int speed = 3;
	int start = 0;
	int end = 400;

	public LavaFireBall(int x, int y) {
		super(x, y, "res/Mario Textures/Lava Fire Ball.png");
	}

	@Override
	public void act() {

		if (start < 0)
			speed *= changeDir;

		if (start >= end)
			speed *= changeDir;

		start += speed;
		this.setY(this.getY() - speed);
	}
}
