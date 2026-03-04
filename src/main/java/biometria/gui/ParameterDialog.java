package biometria.gui;

import javax.swing.*;
import java.awt.*;

public class ParameterDialog {

    public static Integer showSliderDialog(
            Component parent,
            String title,
            String label,
            int min,
            int max,
            int initial
    ) {
        JPanel panel = createSliderPanel(label, min, max, initial);

        int result = JOptionPane.showConfirmDialog(
                parent,
                panel,
                title,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            // pobieranie wartości ze slidera
            return (Integer) panel.getClientProperty("value");
        }

        return null;
    }


    private static JPanel createSliderPanel(String label, int min, int max, int initial) {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // label opisowy
        JLabel titleLabel = new JLabel(label);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(15));

        // slider
        JSlider slider = new JSlider(JSlider.HORIZONTAL, min, max, initial);
        slider.setMajorTickSpacing(Math.max(1, (max - min) / 5));
        slider.setMinorTickSpacing(Math.max(1, (max - min) / 20));
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(slider);
        mainPanel.add(Box.createVerticalStrut(15));

        // panel z dokładną wartością (spinner)
        JPanel valuePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        valuePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel valueLabel = new JLabel("Dokładna wartość:");
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(initial, min, max, 1));
        ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().setColumns(5);

        valuePanel.add(valueLabel);
        valuePanel.add(spinner);
        mainPanel.add(valuePanel);

        // synchronizacja wartości ze slider i spinner
        slider.addChangeListener(e -> {
            spinner.setValue(slider.getValue());
            mainPanel.putClientProperty("value", slider.getValue());
        });

        spinner.addChangeListener(e -> {
            slider.setValue((Integer) spinner.getValue());
            mainPanel.putClientProperty("value", (Integer) spinner.getValue());
        });

        // inicjalizacja wartości
        mainPanel.putClientProperty("value", initial);

        return mainPanel;
    }
}