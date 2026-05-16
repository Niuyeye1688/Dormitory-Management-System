package com.dormitory.util;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.SecureRandom;

public class VerifyCodeUtil {

    private static final String SESSION_KEY = "verifyCode";
    private static final int WIDTH = 120;
    private static final int HEIGHT = 40;
    private static final int CODE_LENGTH = 4;
    private static final String CHARACTERS = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    public static void generate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();

        // 背景色
        g.setColor(new Color(240, 240, 240));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // 干扰线
        for (int i = 0; i < 20; i++) {
            g.setColor(new Color(RANDOM.nextInt(200), RANDOM.nextInt(200), RANDOM.nextInt(200)));
            int x1 = RANDOM.nextInt(WIDTH);
            int y1 = RANDOM.nextInt(HEIGHT);
            int x2 = RANDOM.nextInt(WIDTH);
            int y2 = RANDOM.nextInt(HEIGHT);
            g.drawLine(x1, y1, x2, y2);
        }

        // 生成验证码
        StringBuilder code = new StringBuilder();
        g.setFont(new Font("Arial", Font.BOLD, 28));
        for (int i = 0; i < CODE_LENGTH; i++) {
            String ch = String.valueOf(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
            code.append(ch);
            g.setColor(new Color(RANDOM.nextInt(100), RANDOM.nextInt(100), RANDOM.nextInt(100)));
            g.drawString(ch, 20 + i * 25, 30);
        }

        // 保存到 Session
        HttpSession session = request.getSession();
        session.setAttribute(SESSION_KEY, code.toString());

        g.dispose();
        response.setContentType("image/jpeg");
        ImageIO.write(image, "JPEG", response.getOutputStream());
    }

    public static boolean verify(HttpServletRequest request, String inputCode) {
        HttpSession session = request.getSession();
        String sessionCode = (String) session.getAttribute(SESSION_KEY);
        if (sessionCode == null || inputCode == null) {
            return false;
        }
        boolean result = sessionCode.equalsIgnoreCase(inputCode);
        if (result) {
            session.removeAttribute(SESSION_KEY);
        }
        return result;
    }
}
