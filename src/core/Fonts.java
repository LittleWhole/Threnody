package core;

import org.newdawn.slick.TrueTypeFont;

public final class Fonts {
    public TrueTypeFont MAIN, DAMAGE_NUMBER, DAMAGE_NUMBER_OUTLINE, MENU_TITLE, MENU_BODY, CARD, ARTE;
    public VariableWidthFonts VariableWidth;

    public Fonts() {
        MAIN = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 20), true);
        DAMAGE_NUMBER = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 40), true);
        DAMAGE_NUMBER_OUTLINE = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 45), true);

        VariableWidth = new VariableWidthFonts();
    }

    public class VariableWidthFonts {
        public TrueTypeFont P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14, P15, P16, P17, P18, P19, P20, P21, P22, P23, P24, P25, P26, P27, P28, P29, P30, P31, P32, P33, P34, P35, P36, P37, P38, P39, P40, P41, P42, P43, P44, P45, P46, P47, P48, P49, P50, P51, P52, P53, P54, P55, P56, P57, P58, P59, P60;
        public TrueTypeFont B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18, B19, B20, B21, B22, B23, B24, B25, B26, B27, B28, B29, B30, B31, B32, B33, B34, B35, B36, B37, B38, B39, B40, B41, B42, B43, B44, B45, B46, B47, B48, B49, B50, B51, B52, B53, B54, B55, B56, B57, B58, B59, B60;
        
        public VariableWidthFonts() {
            P1 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 1), true);
            P2 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 2), true);
            P3 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 3), true);
            P4 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 4), true);
            P5 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 5), true);
            P6 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 6), true);
            P7 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 7), true);
            P8 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 8), true);
            P9 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 9), true);
            P10 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 10), true);
            P11 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 11), true);
            P12 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 12), true);
            P13 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 13), true);
            P14 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 14), true);
            P15 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 15), true);
            P16 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 16), true);
            P17 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 17), true);
            P18 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 18), true);
            P19 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 19), true);
            P20 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 20), true);
            P21 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 21), true);
            P22 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 22), true);
            P23 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 23), true);
            P24 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 24), true);
            P25 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 25), true);
            P26 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 26), true);
            P27 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 27), true);
            P28 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 28), true);
            P29 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 29), true);
            P30 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 30), true);
            P31 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 31), true);
            P32 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 32), true);
            P33 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 33), true);
            P34 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 34), true);
            P35 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 35), true);
            P36 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 36), true);
            P37 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 37), true);
            P38 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 38), true);
            P39 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 39), true);
            P40 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 40), true);
            P41 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 41), true);
            P42 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 42), true);
            P43 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 43), true);
            P44 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 44), true);
            P45 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 45), true);
            P46 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 46), true);
            P47 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 47), true);
            P48 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 48), true);
            P49 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 49), true);
            P50 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 50), true);
            P51 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 51), true);
            P52 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 52), true);
            P53 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 53), true);
            P54 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 54), true);
            P55 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 55), true);
            P56 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 56), true);
            P57 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 57), true);
            P58 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 58), true);
            P59 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 59), true);
            P60 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.PLAIN, 60), true);

            B1 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.BOLD, 1), true);
            B2 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.BOLD, 2), true);
            B3 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.BOLD, 3), true);
            B4 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.BOLD, 4), true);
            B5 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.BOLD, 5), true);
            B6 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.BOLD, 6), true);
            B7 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.BOLD, 7), true);
            B8 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.BOLD, 8), true);
            B9 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.BOLD, 9), true);
            B10 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.BOLD, 10), true);
            B11 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.BOLD, 11), true);
            B12 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.BOLD, 12), true);
            B13 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.BOLD, 13), true);
            B14 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.BOLD, 14), true);
            B15 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.BOLD, 15), true);
            B16 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.BOLD, 16), true);
            B17 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.BOLD, 17), true);
            B18 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.BOLD, 18), true);
            B19 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.BOLD, 19), true);
            B20 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.BOLD, 20), true);
            B21 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.BOLD, 21), true);
            B22 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.BOLD, 22), true);
            B23 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.BOLD, 23), true);
            B24 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.BOLD, 24), true);
            B25 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.BOLD, 25), true);
            B26 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.BOLD, 26), true);
            B27 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.BOLD, 27), true);
            B28 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.BOLD, 28), true);
            B29 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.BOLD, 29), true);
            B30 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.BOLD, 30), true);
            B31 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.BOLD, 31), true);
            B32 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.BOLD, 32), true);
            B33 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.BOLD, 33), true);
            B34 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.BOLD, 34), true);
            B35 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.BOLD, 35), true);
            B36 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.BOLD, 36), true);
            B37 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.BOLD, 37), true);
            B38 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.BOLD, 38), true);
            B39 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.BOLD, 39), true);
            B40 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.BOLD, 40), true);
            B41 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.BOLD, 41), true);
            B42 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.BOLD, 42), true);
            B43 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.BOLD, 43), true);
            B44 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.BOLD, 44), true);
            B45 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.BOLD, 45), true);
            B46 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.BOLD, 46), true);
            B47 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.BOLD, 47), true);
            B48 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.BOLD, 48), true);
            B49 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.BOLD, 49), true);
            B50 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.BOLD, 50), true);
            B51 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.BOLD, 51), true);
            B52 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.BOLD, 52), true);
            B53 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.BOLD, 53), true);
            B54 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.BOLD, 54), true);
            B55 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.BOLD, 55), true);
            B56 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.BOLD, 56), true);
            B57 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.BOLD, 57), true);
            B58 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.BOLD, 58), true);
            B59 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.BOLD, 59), true);
            B60 = new TrueTypeFont(new java.awt.Font("Bahnschrift", java.awt.Font.BOLD, 60), true);
        }

        public TrueTypeFont plainFont(int size) {
            return switch (size) {
                case 1 -> P1; case 2 -> P2; case 3 -> P3; case 4 -> P4; case 5 -> P5; case 6 -> P6; case 7 -> P7; case 8 -> P8; case 9 -> P9; case 10 -> P10;
                case 11 -> P11; case 12 -> P12; case 13 -> P13; case 14 -> P14; case 15 -> P15; case 16 -> P16; case 17 -> P17; case 18 -> P18; case 19 -> P19; case 20 -> P20;
                case 21 -> P21; case 22 -> P22; case 23 -> P23; case 24 -> P24; case 25 -> P25; case 26 -> P26; case 27 -> P27; case 28 -> P28; case 29 -> P29; case 30 -> P30;
                case 31 -> P31; case 32 -> P32; case 33 -> P33; case 34 -> P34; case 35 -> P35; case 36 -> P36; case 37 -> P37; case 38 -> P38; case 39 -> P39; case 40 -> P40;
                case 41 -> P41; case 42 -> P42; case 43 -> P43; case 44 -> P44; case 45 -> P45; case 46 -> P46; case 47 -> P47; case 48 -> P48; case 49 -> P49; case 50 -> P50;
                case 51 -> P51; case 52 -> P52; case 53 -> P53; case 54 -> P54; case 55 -> P55; case 56 -> P56; case 57 -> P57; case 58 -> P58; case 59 -> P59; case 60 -> P60;
                default -> P60;
            };
        }

        public TrueTypeFont boldFont(int size) {
            return switch (size) {
                case 1 -> B1; case 2 -> B2; case 3 -> B3; case 4 -> B4; case 5 -> B5; case 6 -> B6; case 7 -> B7; case 8 -> B8; case 9 -> B9; case 10 -> B10;
                case 11 -> B11; case 12 -> B12; case 13 -> B13; case 14 -> B14; case 15 -> B15; case 16 -> B16; case 17 -> B17; case 18 -> B18; case 19 -> B19; case 20 -> B20;
                case 21 -> B21; case 22 -> B22; case 23 -> B23; case 24 -> B24; case 25 -> B25; case 26 -> B26; case 27 -> B27; case 28 -> B28; case 29 -> B29; case 30 -> B30;
                case 31 -> B31; case 32 -> B32; case 33 -> B33; case 34 -> B34; case 35 -> B35; case 36 -> B36; case 37 -> B37; case 38 -> B38; case 39 -> B39; case 40 -> B40;
                case 41 -> B41; case 42 -> B42; case 43 -> B43; case 44 -> B44; case 45 -> B45; case 46 -> B46; case 47 -> B47; case 48 -> B48; case 49 -> B49; case 50 -> B50;
                case 51 -> B51; case 52 -> B52; case 53 -> B53; case 54 -> B54; case 55 -> B55; case 56 -> B56; case 57 -> B57; case 58 -> B58; case 59 -> B59; case 60 -> B60;
                default -> B60;
            };
        }
    }
}
