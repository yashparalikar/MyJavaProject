import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.ArrayList;

/**
 * A simple class to hold information about a single student.
 */
class Student {
    String id;
    String name;
    String grade;

    public Student(String id, String name, String grade) {
        this.id = id;
        this.name = name;
        this.grade = grade;
    }
}

/**
 * The main class for our attractive and modern application.
 * This version is corrected to run without external files.
 */
public class CorrectedStudentManagementSystem extends JFrame {

    private ArrayList<Student> studentList = new ArrayList<>();
    private JTextField idField, nameField, gradeField;
    private JButton addButton, deleteButton, clearButton;
    private JTable studentTable;
    private DefaultTableModel tableModel;
    private JLabel studentCountLabel;

    private Color primaryColor = new Color(30, 30, 30);
    private Color secondaryColor = new Color(45, 45, 45);
    private Color accentColor = new Color(52, 152, 219);
    private Color textColor = new Color(220, 220, 220);
    private Font mainFont = new Font("Segoe UI", Font.PLAIN, 16);
    private Font titleFont = new Font("Segoe UI", Font.BOLD, 28);

    public CorrectedStudentManagementSystem() {
        setTitle("Student Management System");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(primaryColor);

        // --- Header Panel ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(primaryColor);
        headerPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        JLabel titleLabel = new JLabel("Student Dashboard");
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.WEST);

        // --- Input Panel ---
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        inputPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        inputPanel.setBackground(secondaryColor);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        studentCountLabel = new JLabel("Total Students: 0");
        studentCountLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        studentCountLabel.setForeground(accentColor);
        gbc.gridwidth = 2;
        inputPanel.add(studentCountLabel, gbc);
        gbc.gridwidth = 1;

        gbc.gridy++;
        inputPanel.add(createStyledLabel("Student ID:"), gbc);
        gbc.gridy++;
        idField = createStyledTextField();
        inputPanel.add(idField, gbc);
        
        gbc.gridy++;
        inputPanel.add(createStyledLabel("Name:"), gbc);
        gbc.gridy++;
        nameField = createStyledTextField();
        inputPanel.add(nameField, gbc);

        gbc.gridy++;
        inputPanel.add(createStyledLabel("Grade:"), gbc);
        gbc.gridy++;
        gradeField = createStyledTextField();
        inputPanel.add(gradeField, gbc);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        buttonPanel.setOpaque(false);
        addButton = createStyledButton("Add");
        deleteButton = createStyledButton("Delete");
        clearButton = createStyledButton("Clear");
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        
        gbc.gridy++;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.insets = new Insets(20, 0, 0, 0);
        inputPanel.add(buttonPanel, gbc);

        // --- Table ---
        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Grade"}, 0);
        studentTable = new JTable(tableModel);
        styleTable();
        JScrollPane tableScrollPane = new JScrollPane(studentTable);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder());

        // --- Layout ---
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, inputPanel, tableScrollPane);
        splitPane.setDividerLocation(320);
        splitPane.setBorder(null);

        add(headerPanel, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);

        // --- Action Listeners ---
        addButton.addActionListener(e -> addStudent());
        deleteButton.addActionListener(e -> deleteStudent());
        clearButton.addActionListener(e -> clearInputFields());

        setVisible(true);
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(textColor);
        label.setFont(mainFont);
        return label;
    }

    private JTextField createStyledTextField() {
        JTextField textField = new JTextField(15);
        textField.setFont(mainFont);
        textField.setBackground(primaryColor);
        textField.setForeground(textColor);
        textField.setCaretColor(Color.WHITE);
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, accentColor),
            new EmptyBorder(5, 5, 5, 5)
        ));
        return textField;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(accentColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void styleTable() {
        studentTable.setFont(mainFont);
        studentTable.setRowHeight(30);
        studentTable.setBackground(secondaryColor);
        studentTable.setForeground(textColor);
        studentTable.setSelectionBackground(accentColor);
        studentTable.setSelectionForeground(Color.WHITE);
        studentTable.setShowGrid(false);

        JTableHeader header = studentTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 16));
        header.setBackground(primaryColor);
        header.setForeground(Color.WHITE);
    }

    private void addStudent() {
        String id = idField.getText();
        String name = nameField.getText();
        String grade = gradeField.getText();
        if (id.isEmpty() || name.isEmpty() || grade.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        studentList.add(new Student(id, name, grade));
        tableModel.addRow(new Object[]{id, name, grade});
        updateStudentCount();
        clearInputFields();
    }

    private void deleteStudent() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        studentList.remove(selectedRow);
        tableModel.removeRow(selectedRow);
        updateStudentCount();
    }

    private void clearInputFields() {
        idField.setText("");
        nameField.setText("");
        gradeField.setText("");
    }

    private void updateStudentCount() {
        studentCountLabel.setText("Total Students: " + studentList.size());
    }

    public static void main(String[] args) {
        // Run the GUI creation safely
        SwingUtilities.invokeLater(() -> new CorrectedStudentManagementSystem());
    }
}