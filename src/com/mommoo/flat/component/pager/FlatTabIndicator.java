package com.mommoo.flat.component.pager;

import com.mommoo.animation.AnimationAdapter;
import com.mommoo.util.ScreenManager;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FlatTabIndicator extends JPanel {
    private static final ScreenManager SCREEN = ScreenManager.getInstance();
    private final Rectangle BOUNDS = new Rectangle();
    private IndicatorAnimator ANIMATOR = new IndicatorAnimator();
    private Color indicatorColor = FlatPageColor.getDefaultFlatPagerColor().getFocusInColor();

    public FlatTabIndicator(){
        setOpaque(false);
        setPreferredSize(new Dimension(SCREEN.dip2px(1), SCREEN.dip2px(4)));
    }

    public void initBounds(Rectangle bounds){
        BOUNDS.setBounds(bounds);
        repaint();
    }

    public void setFlatPageColor(FlatPageColor flatPageColor){
        indicatorColor = flatPageColor.getFocusInColor();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D graphics2D = (Graphics2D)g;
        graphics2D.setColor(indicatorColor);
        graphics2D.fill(BOUNDS);
    }

    public void indicate(Rectangle bounds){
        ANIMATOR
                .stop()
                .start(bounds.x - BOUNDS.x,  bounds.width - BOUNDS.width);
    }

    private class IndicatorAnimator extends AbstractAnimator{
        public IndicatorAnimator(){
            setAnimationListener(new AnimationAdapter(){
                private final Rectangle previousBounds = new Rectangle();

                @Override
                public void onStart() {
                    previousBounds.setBounds(BOUNDS);
                }

                @Override
                public void onAnimation(List<Double> resultList) {
                    int x = resultList.get(0).intValue();
                    int w = resultList.get(1).intValue();
                    BOUNDS.x = previousBounds.x + x;
                    BOUNDS.width = previousBounds.width + w;
                    repaint();
                }
            });
        }
    }
}