package com.nexus.arena.ui;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.scene.SnapshotParameters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

// CREDITS AI -------- CLAUDE

/**
 * 3D Particle Morphing Splash — ER Diagram Project style.
 * Features: connecting lines between nearby particles, smooth easing,
 * depth-based glow, mouse repulsion, single turquoise color.
 */
public class ParticleSplashScreen {

    private final Canvas canvas;
    private final GraphicsContext gc;
    private final List<Particle> particles = new ArrayList<>();
    private final Random random = new Random();

    private static final int PARTICLE_COUNT = 2500;
    private static final double FOV = 500;
    private static final double CAMERA_DIST = 900;
    private static final double CONNECTION_DIST = 20; // Reduced radius for the spider web effect
    private static final Color CYAN = Color.web("#00d4ff");

    private double mouseX = -10000, mouseY = -10000;
    private AnimationTimer timer;
    private boolean textFormed = false;
    private double time = 0;

    public ParticleSplashScreen(Canvas canvas) {
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
        initParticles();
    }

    private void initParticles() {
        for (int i = 0; i < PARTICLE_COUNT; i++) {
            Particle p = new Particle();
            p.x = (random.nextDouble() - 0.5) * 1400;
            p.y = (random.nextDouble() - 0.5) * 900;
            p.z = (random.nextDouble() - 0.5) * 600;
            p.tx = p.x; p.ty = p.y; p.tz = p.z;
            p.baseOpacity = 0.3 + random.nextDouble() * 0.7;
            particles.add(p);
        }
    }

    public void setMouse(double mx, double my) { mouseX = mx; mouseY = my; }
    public void clearMouse() { mouseX = -10000; mouseY = -10000; }

    public void setTargetText(String text, double scale) {
        double w = 1200, h = 280;
        Canvas temp = new Canvas(w, h);
        GraphicsContext tgc = temp.getGraphicsContext2D();
        tgc.setFill(Color.BLACK);
        tgc.fillRect(0, 0, w, h);
        tgc.setFont(Font.font("Segoe UI", FontWeight.BLACK, 140));
        tgc.setFill(Color.WHITE);
        tgc.setTextAlign(TextAlignment.CENTER);
        tgc.fillText(text, w / 2, h / 2 + 48);

        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.BLACK);
        PixelReader pr = temp.snapshot(params, null).getPixelReader();

        List<double[]> points = new ArrayList<>();
        for (int y = 0; y < (int) h; y += 3) {
            for (int x = 0; x < (int) w; x += 3) {
                if (pr.getColor(x, y).getBrightness() > 0.4) {
                    points.add(new double[]{
                        (x - w / 2) * scale,
                        (y - h / 2) * scale,
                        (random.nextDouble() - 0.5) * 8
                    });
                }
            }
        }
        if (points.isEmpty()) return;
        Collections.shuffle(points);

        for (int i = 0; i < particles.size(); i++) {
            double[] t = points.get(i % points.size());
            Particle p = particles.get(i);
            p.tx = t[0]; p.ty = t[1]; p.tz = t[2];
        }
        textFormed = true;
    }

    public void explode() {
        textFormed = false;
        for (Particle p : particles) {
            double angle = random.nextDouble() * Math.PI * 2;
            double speed = 800 + random.nextDouble() * 1200;
            p.tx = Math.cos(angle) * speed;
            p.ty = Math.sin(angle) * speed * 0.6;
            p.tz = (random.nextDouble() - 0.5) * 2000;
            p.vx += (random.nextDouble() - 0.5) * 40;
            p.vy += (random.nextDouble() - 0.5) * 40;
        }
    }

    public void start() {
        timer = new AnimationTimer() {
            long last = 0;
            @Override public void handle(long now) {
                if (last == 0) { last = now; return; }
                double dt = (now - last) / 1e9;
                time += dt;
                update(dt);
                render();
                last = now;
            }
        };
        timer.start();
    }

    public void stop() { if (timer != null) timer.stop(); }

    private void update(double dt) {
        double cx = canvas.getWidth() / 2;
        double cy = canvas.getHeight() / 2;

        for (Particle p : particles) {
            // Smooth spring physics
            double dx = p.tx - p.x, dy = p.ty - p.y, dz = p.tz - p.z;
            p.vx += dx * 0.06;
            p.vy += dy * 0.06;
            p.vz += dz * 0.06;
            p.vx *= 0.82;
            p.vy *= 0.82;
            p.vz *= 0.82;
            p.x += p.vx;
            p.y += p.vy;
            p.z += p.vz;

            // Idle wobble when text is formed
            if (textFormed) {
                p.x += Math.sin(time * 1.2 + p.phase) * 0.3;
                p.y += Math.cos(time * 0.8 + p.phase * 1.3) * 0.3;
            }

            // Project to screen
            double factor = FOV / (p.z + CAMERA_DIST);
            p.sx = p.x * factor + cx;
            p.sy = p.y * factor + cy;
            p.screenSize = Math.max(0.8, 3.5 * factor);
            p.depth = Math.max(0.15, Math.min(1.0, (p.z + 500) / 1000.0));

            // Mouse repulsion (only when text is formed with a smaller radius)
            if (textFormed) {
                double distM = Math.sqrt(Math.pow(mouseX - p.sx, 2) + Math.pow(mouseY - p.sy, 2));
                if (distM < 80) { // Reduced radius
                    double force = (80 - distM) / 80.0;
                    double angle = Math.atan2(p.sy - mouseY, p.sx - mouseX);
                    p.vx += Math.cos(angle) * force * 18;
                    p.vy += Math.sin(angle) * force * 18;
                    p.vz += force * 40;
                }
            }
        }
    }

    private void render() {
        double w = canvas.getWidth(), h = canvas.getHeight();
        gc.clearRect(0, 0, w, h);

        // Background gradient (dark void)
        gc.setFill(Color.web("#060610"));
        gc.fillRect(0, 0, w, h);

        // Subtle radial glow in center
        gc.setFill(Color.color(0, 0.83, 1.0, 0.015));
        gc.fillOval(w / 2 - 400, h / 2 - 300, 800, 600);

        // Draw connecting lines between nearby particles (ER diagram style)
        gc.setLineWidth(0.5);
        for (int i = 0; i < particles.size(); i++) {
            Particle a = particles.get(i);
            for (int j = i + 1; j < particles.size(); j++) {
                Particle b = particles.get(j);
                double dist = Math.sqrt(Math.pow(a.sx - b.sx, 2) + Math.pow(a.sy - b.sy, 2));
                if (dist < CONNECTION_DIST) {
                    double alpha = (1 - dist / CONNECTION_DIST) * 0.15 * a.depth * b.depth;
                    gc.setStroke(Color.color(0, 0.83, 1.0, alpha));
                    gc.strokeLine(a.sx, a.sy, b.sx, b.sy);
                }
            }
        }

        // Draw particles
        for (Particle p : particles) {
            double opacity = p.depth * p.baseOpacity;
            double size = p.screenSize;

            // Outer glow
            if (size > 1.5) {
                gc.setFill(Color.color(0, 0.83, 1.0, opacity * 0.08));
                gc.fillOval(p.sx - size * 2, p.sy - size * 2, size * 4, size * 4);
            }

            // Core particle
            gc.setFill(Color.color(0, 0.83, 1.0, opacity));
            gc.fillOval(p.sx - size / 2, p.sy - size / 2, size, size);

            // Bright center for large particles
            if (size > 2.0) {
                gc.setFill(Color.color(0.7, 0.95, 1.0, opacity * 0.6));
                double inner = size * 0.35;
                gc.fillOval(p.sx - inner / 2, p.sy - inner / 2, inner, inner);
            }
        }
    }

    private static class Particle {
        double x, y, z;
        double tx, ty, tz;
        double vx, vy, vz;
        double sx, sy;       // Screen coordinates
        double screenSize;
        double depth;
        double baseOpacity;
        double phase = Math.random() * Math.PI * 2;
    }
}
