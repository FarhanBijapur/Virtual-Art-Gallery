import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

abstract class Artwork {
    private String title;
    private String artist;
    private int year;
    private String description;
    private double price;
    private String imagePath;

    public Artwork(String title, String artist, int year, String description, double price, String imagePath) {
        this.title = title;
        this.artist = artist;
        this.year = year;
        this.description = description;
        this.price = price;
        this.imagePath = imagePath;
    }

    public abstract String displayDetails();

    public String getTitle() { return title; }
    public String getArtist() { return artist; }
    public int getYear() { return year; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public String getImagePath() { return imagePath; }
}

class Painting extends Artwork {
    public Painting(String title, String artist, int year, String description, double price, String imagePath) {
        super(title, artist, year, description, price, imagePath);
    }

    @Override
    public String displayDetails() {
        return String.format("<html><h2 style='color:#333;'>%s</h2><p style='color:#555;'><strong>Artist:</strong> %s<br><strong>Year:</strong> %d<br><strong>Description:</strong> %s<br><strong>Price:</strong> $%.2f</p></html>",
                getTitle(), getArtist(), getYear(), getDescription(), getPrice());
    }
}

public class Virtual_Art_Gallery extends JFrame {
    private JEditorPane artworkDetails;
    private JButton nextArtworkButton;
    private JButton previousArtworkButton;
    private JLabel artworkImageLabel;
    private Artwork[] artworks;
    private int currentArtworkIndex = 0;

    public Virtual_Art_Gallery() {
        artworks = new Artwork[]{
            new Painting("Alone", "Vincent Van Gogh", 2021, "A thought-provoking piece that evokes solitude.", 200, "C:\\Users\\LENOVO\\OneDrive\\Desktop\\Java\\images\\Alone.jpeg"),
            new Painting("David", "Michelangelo", 1504, "A marble statue representing the biblical hero David.", 2000000, "C:\\Users\\LENOVO\\OneDrive\\Desktop\\Java\\images\\david.jpeg"),
            new Painting("Freedom", "Pablo Picasso", 2020, "An inspiring piece symbolizing liberation.", 300, "C:\\Users\\LENOVO\\OneDrive\\Desktop\\Java\\images\\freedom.jpeg"),
            new Painting("Minimalist", "Agnes Martin", 2019, "A beautiful representation of minimalism in art.", 150, "C:\\Users\\LENOVO\\OneDrive\\Desktop\\Java\\images\\Minimalist.jpeg"),
            new Painting("Mona Lisa", "Leonardo da Vinci", 1503, "The world-famous portrait known for her enigmatic smile.", 1000000, "C:\\Users\\LENOVO\\OneDrive\\Desktop\\Java\\images\\monalisa.jpg"),
            new Painting("Night Sky", "Claude Monet", 2021, "A captivating depiction of a starry night.", 250, "C:\\Users\\LENOVO\\OneDrive\\Desktop\\Java\\images\\night_sky.jpeg"),
            new Painting("Rhythm", "Jackson Pollock", 2020, "An artistic representation of rhythm and movement.", 400, "C:\\Users\\LENOVO\\OneDrive\\Desktop\\Java\\images\\rhythm.jpeg"),
            new Painting("Scenery", "Georgia O'Keeffe", 2021, "A beautiful landscape capturing nature's essence.", 350, "C:\\Users\\LENOVO\\OneDrive\\Desktop\\Java\\images\\Scenery.jpeg"),
            new Painting("Starry Night", "Vincent Van Gogh", 1889, "A famous oil painting depicting a swirling night sky.", 1000000, "C:\\Users\\LENOVO\\OneDrive\\Desktop\\Java\\images\\starry_night.jpeg"),
            new Painting("The Kiss", "Gustav Klimt", 1907, "A beautiful painting representing love and intimacy.", 1200000, "C:\\Users\\LENOVO\\OneDrive\\Desktop\\Java\\images\\the_kiss.jpeg"),
            new Painting("The Night Watch", "Rembrandt", 1642, "A famous group portrait of a city militia.", 700000, "C:\\Users\\LENOVO\\OneDrive\\Desktop\\Java\\images\\the_night_watch.jpg"),
            new Painting("The Scream", "Edvard Munch", 1893, "An iconic expressionist painting representing existential dread.", 800000, "C:\\Users\\LENOVO\\OneDrive\\Desktop\\Java\\images\\the_scream.jpg"),
            new Painting("3 Idiots", "Rajkumar Hirani", 2021, "A creative depiction based on popular culture.", 150, "C:\\Users\\LENOVO\\OneDrive\\Desktop\\Java\\images\\3iditos.jpeg"),
            new Painting("3 Idiots", "Rajkumar Hirani", 2021, "A creative depiction based on popular culture.", 150, "C:\\Users\\LENOVO\\OneDrive\\Desktop\\Java\\images\\download.png")

        };

        setTitle("Virtual Art Gallery");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        artworkDetails = new JEditorPane();
        artworkDetails.setEditable(false);
        artworkDetails.setFont(new Font("Arial", Font.PLAIN, 16));
        artworkDetails.setBackground(Color.WHITE);
        artworkDetails.setForeground(new Color(80, 80, 80));
        artworkDetails.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        artworkDetails.setContentType("text/html");
        add(new JScrollPane(artworkDetails), BorderLayout.SOUTH);

        artworkImageLabel = new JLabel();
        artworkImageLabel.setHorizontalAlignment(JLabel.CENTER);
        add(artworkImageLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setBackground(Color.WHITE);
        previousArtworkButton = new JButton("Previous");
        nextArtworkButton = new JButton("Next");
        buttonPanel.add(previousArtworkButton);
        buttonPanel.add(nextArtworkButton);
        add(buttonPanel, BorderLayout.NORTH);

        previousArtworkButton.setFont(new Font("Arial", Font.BOLD, 18));
        nextArtworkButton.setFont(new Font("Arial", Font.BOLD, 18));
        previousArtworkButton.setBackground(new Color(50, 150, 255));
        nextArtworkButton.setBackground(new Color(50, 150, 255));
        previousArtworkButton.setForeground(Color.WHITE);
        nextArtworkButton.setForeground(Color.WHITE);
        previousArtworkButton.setFocusPainted(false);
        nextArtworkButton.setFocusPainted(false);

        previousArtworkButton.addActionListener(e -> showPreviousArtwork());
        nextArtworkButton.addActionListener(e -> showNextArtwork());

        displayArtworkDetails();
    }

    private void displayArtworkDetails() {
        Artwork currentArtwork = artworks[currentArtworkIndex];
        artworkDetails.setText(currentArtwork.displayDetails());
        ImageIcon originalIcon = new ImageIcon(currentArtwork.getImagePath());
        Image scaledImage = originalIcon.getImage().getScaledInstance(600, 400, Image.SCALE_SMOOTH);
        artworkImageLabel.setIcon(new ImageIcon(scaledImage));
    }

    private void showNextArtwork() {
        currentArtworkIndex = (currentArtworkIndex + 1) % artworks.length;
        displayArtworkDetails();
    }

    private void showPreviousArtwork() {
        currentArtworkIndex = (currentArtworkIndex - 1 + artworks.length) % artworks.length;
        displayArtworkDetails();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Virtual_Art_Gallery gallery = new Virtual_Art_Gallery();
            gallery.setVisible(true);
        });
    }
}
