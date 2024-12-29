package utilz;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class LoadSave {
    private static boolean isLoaded = false;
    public static final LoadSave loadSave = new LoadSave();
    public ArrayList<BufferedImage> Imgs = new ArrayList<>();

    private final BufferedImage
            WPawnImg, WBishopImg, WNightImg, WRookImg, WQueenImg, WKingImg,
            BOARD_IMG, LOGO, StockfishLogo, PlayerIcon,
            BPawnImg, BBishopImg, BNightImg, BRookImg, BQueenImg, BKingImg;




    private final BufferedImage Brilliant,
            Grate,
            Best_Move,
            Excellent,
            Good,
            Underrated,
            Book,
            Inaccuracy,
            Mistake,
            Miss,
            Blunder,
            What;


    private LoadSave() {
        WPawnImg = loadImg(Png.WPAWN_PNG);
        WBishopImg = loadImg(Png.WBISHOP_PNG);
        WNightImg = loadImg(Png.WKNIGHT_PNG);
        WRookImg = loadImg(Png.WROOK_PNG);
        WQueenImg = loadImg(Png.WQUEEN_PNG);
        WKingImg = loadImg(Png.WKING_PNG);

        BOARD_IMG = loadImg(Png.BOARD_PNG);
        LOGO = loadImg(Png.LOGO_PNG);
        StockfishLogo = loadImg(Png.StockfishLogo);
        PlayerIcon = loadImg(Png.PlayerIcon);

        Brilliant = loadImg(Png.Brilliant_PNG);
        Grate = loadImg(Png.Grate_PNG);
        Best_Move = loadImg(Png.BestMove_PNG);
        Excellent = loadImg(Png.Excellent_PNG);
        Good = loadImg(Png.Good_PNG);
        Underrated = loadImg(Png.Underrated_PNG);
        Book = loadImg(Png.Book_PNG);
        Inaccuracy = loadImg(Png.Inaccuracy_PNG);
        Mistake = loadImg(Png.Mistake_PNG);
        Miss = loadImg(Png.Miss_PNG);
        Blunder = loadImg(Png.Blunder_PNG);
        What = loadImg(Png.What_PNG);


        BPawnImg = loadImg(Png.BPAWN_PNG);
        BBishopImg = loadImg(Png.BBISHOP_PNG);
        BNightImg = loadImg(Png.BKNIGHT_PNG);
        BRookImg = loadImg(Png.BROOK_PNG);
        BQueenImg = loadImg(Png.BQUEEN_PNG);
        BKingImg = loadImg(Png.BKING_PNG);

        for (BufferedImage tempImg : Imgs)
            if (tempImg == null)
                System.out.println("can't load the img");

        isLoaded = true;
    }

    public static LoadSave getLoadSave() {
        return loadSave;
    }


    public BufferedImage loadImg(String name) {
        InputStream is = getClass().getResourceAsStream("res/"+name);
        BufferedImage img = null;
        try {
            img = ImageIO.read(is);
            Imgs.add(img);
            return img;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return img;
    }

    public ArrayList<BufferedImage> getImgs() {
        if (isLoaded)
            return Imgs;
        else
            System.out.println("there is a problem with the imges list ");
        return null;
    }

    public BufferedImage getWPawnImg() {
        if (isLoaded)
            return WPawnImg;
        else
            System.out.println("there is a problem with the imges list ");
        return null;

    }

    public BufferedImage getWBishopImg() {
        if (isLoaded)
            return WBishopImg;
        else
            System.out.println("there is a problem with the White Bishop img");
        return null;

    }

    public BufferedImage getWNightImg() {
        if (isLoaded)
            return WNightImg;
        else
            System.out.println("there is a problem with the White Night img");
        return null;

    }

    public BufferedImage getWRookImg() {
        if (isLoaded)
            return WRookImg;
        else
            System.out.println("there is a problem with the White Rook img ");
        return null;

    }

    public BufferedImage getWQueenImg() {
        if (isLoaded)
            return WQueenImg;
        else
            System.out.println("there is a problem with the White Queen img");
        return null;

    }

    public BufferedImage getWKingImg() {
        if (isLoaded)
            return WKingImg;
        else
            System.out.println("there is a problem with the White King img ");
        return null;

    }

    public BufferedImage getBOARD_IMG() {
        if (isLoaded)
            return BOARD_IMG;
        else
            System.out.println("there is a problem with the Bord img");
        return null;

    }

    public BufferedImage getBPawnImg() {
        if (isLoaded)
            return BPawnImg;
        else
            System.out.println("there is a problem with the Black Pawn img");
        return null;

    }

    public BufferedImage getBBishopImg() {
        if (isLoaded)
            return BBishopImg;
        else
            System.out.println("there is a problem with the Black Bishop img");
        return null;

    }

    public BufferedImage getBNightImg() {
        if (isLoaded)
            return BNightImg;
        else
            System.out.println("there is a problem with the Black Night img");
        return null;

    }

    public BufferedImage getBRookImg() {
        if (isLoaded)
            return BRookImg;
        else
            System.out.println("there is a problem with the Black Rook img ");
        return null;

    }

    public BufferedImage getBQueenImg() {
        if (isLoaded)
            return BQueenImg;
        else
            System.out.println("there is a problem with the Black Queen img");
        return null;

    }

    public BufferedImage getBKingImg() {
        if (isLoaded)
            return BKingImg;
        else
            System.out.println("there is a problem with the Black King img ");
        return null;
    }

    public BufferedImage getLogoImg() {
        if (isLoaded)
            return LOGO;
        else
            System.out.println("there is a problem with the Logo img ");
        return null;
    }

    public BufferedImage getStockfishLogo() {
        if (isLoaded)
            return StockfishLogo;
        else
            System.out.println("there is a problem with the StockfishLogo img ");
        return null;
    }

    public BufferedImage getPlayerIcon() {
        if (isLoaded)
            return PlayerIcon;
        else
            System.out.println("there is a problem with the PlayerIcon img ");
        return null;
    }

    public BufferedImage getBrilliantImg() {
        if (isLoaded)
            return Brilliant;
        else
            System.out.println("there is a problem with the Brilliant img ");
        return null;

    }

    public BufferedImage getGrateImg() {
        if (isLoaded)
            return Grate;
        else
            System.out.println("there is a problem with the Grate img ");
        return null;

    }

    public BufferedImage getBest_MoveImg() {
        if (isLoaded)
            return Best_Move;
        else
            System.out.println("there is a problem with the Best_Move img ");
        return null;

    }

    public BufferedImage getExcellentImg() {
        if (isLoaded)
            return Excellent;
        else
            System.out.println("there is a problem with the Excellent img ");
        return null;

    }

    public BufferedImage getGoodImg() {
        if (isLoaded)
            return Good;
        else
            System.out.println("there is a problem with the Good img ");
        return null;

    }

    public BufferedImage getUnderratedImg() {
        if (isLoaded)
            return Underrated;
        else
            System.out.println("there is a problem with the Underrated img ");
        return null;

    }

    public BufferedImage getBookImg() {
        if (isLoaded)
            return Book;
        else
            System.out.println("there is a problem with the Book img ");
        return null;

    }

    public BufferedImage getInaccuracyImg() {
        if (isLoaded)
            return Inaccuracy;
        else
            System.out.println("there is a problem with the Inaccuracy img ");
        return null;

    }

    public BufferedImage getMistakeImg() {
        if (isLoaded)
            return Mistake;
        else
            System.out.println("there is a problem with the Mistake img ");
        return null;

    }

    public BufferedImage getMissImg() {
        if (isLoaded)
            return Miss;
        else
            System.out.println("there is a problem with the Miss img ");
        return null;

    }

    public BufferedImage getBlunderImg() {
        if (isLoaded)
            return Blunder;
        else
            System.out.println("there is a problem with the Blunder img ");
        return null;

    }

    public BufferedImage getWhatImg() {
        if (isLoaded)
            return What;
        else
            System.out.println("there is a problem with the What img ");
        return null;

    }
}
