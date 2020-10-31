package ru.vsu.cs;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private DrawPanel dp;

    public MainWindow() throws HeadlessException {
        dp = new DrawPanel();
        add(dp);
    }
}
