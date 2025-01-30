package finalSCD;

import java.awt.*;
import javax.swing.*;

public class ToDoListApp extends JFrame {

    private static final long serialVersionUID = 1L;
    private DefaultListModel<String> pendingTaskListModel;
    private DefaultListModel<String> completedTaskListModel;
    private JList<String> pendingTaskList;
    private JList<String> completedTaskList;
    private JTextField taskInputField;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ToDoListApp frame = new ToDoListApp();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public ToDoListApp() {
        // Set basic properties for the JFrame
        setTitle("To-Do List Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header Section
        JLabel headerLabel = new JLabel("To-Do List", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 22));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(headerLabel, BorderLayout.NORTH);

        // Task Lists (Pending and Completed)
        pendingTaskListModel = new DefaultListModel<>();
        completedTaskListModel = new DefaultListModel<>();

        pendingTaskList = new JList<>(pendingTaskListModel);
        completedTaskList = new JList<>(completedTaskListModel);

        JScrollPane pendingTaskScrollPane = new JScrollPane(pendingTaskList);
        JScrollPane completedTaskScrollPane = new JScrollPane(completedTaskList);

        JPanel taskPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        taskPanel.add(createListPanel("Pending Tasks", pendingTaskScrollPane));
        taskPanel.add(createListPanel("Completed Tasks", completedTaskScrollPane));

        add(taskPanel, BorderLayout.CENTER);

        // Input and Control Panel
        JPanel inputPanel = new JPanel(new GridLayout(2, 1));

        // Task Input Panel (Task Description)
        JPanel taskInputPanel = new JPanel(new FlowLayout());
        taskInputField = new JTextField(20);
        JButton addTaskButton = new JButton("Add Task");

        taskInputPanel.add(new JLabel("Task:"));
        taskInputPanel.add(taskInputField);
        taskInputPanel.add(addTaskButton);  // Add button next to the field

        // Control Buttons for Task Actions
        JPanel taskControlPanel = new JPanel(new FlowLayout());
        JButton markDoneButton = new JButton("Mark as Done");
        JButton deleteTaskButton = new JButton("Delete Task");

        // Change button colors
        markDoneButton.setBackground(Color.GREEN);
        deleteTaskButton.setBackground(Color.RED);

        taskControlPanel.add(markDoneButton);
        taskControlPanel.add(deleteTaskButton);

        inputPanel.add(taskInputPanel);
        inputPanel.add(taskControlPanel);

        add(inputPanel, BorderLayout.SOUTH);

        // Action listeners
        addTaskButton.addActionListener(e -> {
            String task = taskInputField.getText().trim();
            if (!task.isEmpty()) {
                pendingTaskListModel.addElement(task);
                taskInputField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a task.");
            }
        });

        // Mark as Done Action
        markDoneButton.addActionListener(e -> {
            int selectedIndex = pendingTaskList.getSelectedIndex();
            if (selectedIndex != -1) {
                String task = pendingTaskListModel.getElementAt(selectedIndex);
                completedTaskListModel.addElement(task + " (Completed)");
                pendingTaskListModel.remove(selectedIndex);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a task to mark as done.");
            }
        });

        // Delete Task Action
        deleteTaskButton.addActionListener(e -> {
            int selectedIndex = pendingTaskList.getSelectedIndex();
            if (selectedIndex != -1) {
                pendingTaskListModel.remove(selectedIndex);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a task to delete.");
            }
        });
    }

    // Helper method to create a list panel with a header
    private JPanel createListPanel(String title, JScrollPane scrollPane) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(title, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(label, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }
}
