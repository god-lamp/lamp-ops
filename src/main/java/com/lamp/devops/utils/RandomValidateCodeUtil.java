package com.lamp.devops.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * @author god-lamp
 * @since 2024-01-03
 */
public class RandomValidateCodeUtil {
    public static final String RANDOM_CODE_KEY = "RANDOM_VALIDATE_CODE_KEY";
    private static final String RAND_STRING = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnPpQqRrSsTtUuVvWwXxYyZz0123456789";
    private final Random random = new Random();

    private final int width = 95;

    private final int height = 25;

    private Font getFont() {
        return new Font("Fixedsys", Font.BOLD, 18);
    }

    private Color getRandColor() {
        int fc = 110;
        int bc = 133;
        int r = fc + this.random.nextInt((bc - fc) - 16);
        int g = fc + this.random.nextInt((bc - fc) - 14);
        int b = fc + this.random.nextInt((bc - fc) - 18);
        return new Color(r, g, b);
    }

    public String getRandomCode(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        BufferedImage image = new BufferedImage(width, height, 4);
        Graphics g = image.getGraphics();
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        g.setColor(getRandColor());
        int lineSize = 40;
        for (int i = 0; i <= lineSize; i++) {
            drawLine(g);
        }
        String randomString = "";
        int num = 5;
        for (int i = 1; i <= num; i++) {
            randomString = drowString(g, randomString, i);
        }
        session.removeAttribute(RANDOM_CODE_KEY);
        session.setAttribute(RANDOM_CODE_KEY, randomString);
        g.dispose();
        try {
            ImageIO.write(image, "JPEG", response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return randomString;
    }

    private String drowString(Graphics g, String randomString, int i) {
        g.setFont(getFont());
        g.setColor(new Color(this.random.nextInt(101), this.random.nextInt(111), this.random.nextInt(121)));
        String rand = String.valueOf(getRandomString(this.random.nextInt(RAND_STRING.length())));
        String randomString2 = randomString + rand;
        g.translate(this.random.nextInt(3), this.random.nextInt(3));
        g.drawString(rand, 13 * i, 16);
        return randomString2;
    }

    private void drawLine(Graphics g) {
        int x = this.random.nextInt(width);
        int y = this.random.nextInt(height);
        int xl = this.random.nextInt(13);
        int yl = this.random.nextInt(15);
        g.drawLine(x, y, x + xl, y + yl);
    }

    public String getRandomString(int num) {
        return String.valueOf(RAND_STRING.charAt(num));
    }
}
