// ==========================================================================
// $Id: TrySorts.java,v 1.1 2006/11/05 03:27:51 jlang Exp $
// CSI2110 Lab code Applet for sorting
// ==========================================================================
// (C)opyright:
//
//   SITE, University of Ottawa
//   800 King Edward Ave.
//   Ottawa, On., K1N 6N5
//   Canada. 
//   http://www.site.uottawa.ca
// 
// Creator: unknown (Lab source without reference), adapted by J.Lang
// Email:   jlang@site.uottawa.ca
// ==========================================================================
// $Log: TrySorts.java,v $
// Revision 1.1  2006/11/05 03:27:51  jlang
// Added lab8 on sorting.
//
//
// ==========================================================================

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class TrySorts extends Applet implements MouseListener, WindowListener {
    IvjEventHandler ivjEventHandler = new IvjEventHandler();
    // private Checkbox ivjBucketSortBox = null;
    private Checkbox ivjHeapSortBox = null;
    private Checkbox ivjQuickSortBox = null;
    private CheckboxGroup ivjSortTypeGroup = null;
    private Button ivjClearOutputButton = null;
    private TextField ivjNumItemsField = null;
    private Checkbox ivjShowOutputBox = null;
    private TextField ivjTimeTakenField = null;
    private TextField ivjMaxElementField = null;
    private Label ivjMaxElementLabel = null;
    private TextArea ivjOutputArea = null;
    private Label ivjOutputLabel = null;
    private Label ivjTimeTakenLabel = null;
    private Button ivjStartButton = null;
    private Button ivjNewButton = null;
    private Label ivjArraySortLabel = null;
    private Checkbox ivjBubbleSortBox = null;
    private Checkbox ivjMergeSortBox = null;
    private Label ivjNumItemsLabel = null;

    protected Long[] d_testSequence = null;


    class IvjEventHandler implements java.awt.event.ActionListener, java.awt.event.TextListener {

        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() == TrySorts.this.getClearOutputButton()) {
                connEtoM3();
            }
            if (e.getSource() == TrySorts.this.getStartButton()) {
                connEtoC2();
            }
            if (e.getSource() == TrySorts.this.getNewButton()) {
                connNew();
            }
        }

        public void textValueChanged(java.awt.event.TextEvent e) {
            if (e.getSource() == TrySorts.this.getNumItemsField()) {
                connEtoC1();
            }
        }

    }

    public boolean checkFields(String arraySizeText, String maxElementText) {
        try {
            isNumeric(arraySizeText);
        } catch (NumberFormatException e) {
            getOutputArea().append("Incorrect value for number of items.\n");
            return false;
        }
        try {
            isNumeric(maxElementText);
        } catch (NumberFormatException e) {
            getOutputArea().append("Incorrect value for maximum element size.\n");
            return false;
        }
        return true;
    }


    /**
     * Try to read all input fields and convert to numbers
     * If error, send message to Output area
     *
     * @param arraySizeText  java.lang.String
     * @param maxElementText java.lang.String
     * @param sortBoxChecked java.awt.Checkbox
     * @return boolean
     */
    public boolean checkFields(String arraySizeText, String maxElementText, Checkbox sortBoxChecked) {
        boolean result = checkFields(arraySizeText, maxElementText);

        if (!result) {
            return false;
        }
        if (sortBoxChecked == null) {
            result = false;
            getOutputArea().append("No sort option checked.\n");
        } else {
            result = true;
        }
        return result;
    }

    /**
     * Check if array size is small enough for screen output
     *
     * @param arraySizeText java.lang.String
     * @return boolean
     */
    public boolean checkOutputEnable(String arraySizeText) {
        int arraySize;
        try {
            arraySize = Integer.parseInt(arraySizeText);
        } catch (NumberFormatException e) {
            arraySize = 0;
        }
        return (arraySize <= 100);
    }

    /**
     * connEtoC1:  (TextField1.text.textValueChanged(java.awt.event.TextEvent) --> TrySorts.checkOutputEnable(Ljava.lang.String;)Z)
     *
     * @return boolean
     */
    private boolean connEtoC1() {
        boolean connEtoC1Result = false;
        try {
            connEtoC1Result = this.checkOutputEnable(getNumItemsField().getText());
            connEtoM1(connEtoC1Result);
            connEtoM4(connEtoC1Result);
        } catch (java.lang.Throwable ivjExc) {
            handleException(ivjExc);
        }
        return connEtoC1Result;
    }

    /**
     * connEtoC2:
     * (StartButton.action.actionPerformed(java.awt.event.ActionEvent)
     * -->
     * TrySorts.doStartButton(Ljava.lang.String;Ljava.lang.String;Ljava.awt.Checkbox;Z)V)
     */
    private void connEtoC2() {
        try {
            this.doStartButton(getNumItemsField().getText(), getMaxElementField().getText(), getSortTypeGroup().getSelectedCheckbox());
        } catch (java.lang.Throwable ivjExc) {
            handleException(ivjExc);
        }
    }

    private void connNew() {
        try {
            this.doNewButton(getNumItemsField().getText(), getMaxElementField().getText());
        } catch (java.lang.Throwable ivjExc) {
            handleException(ivjExc);
        }
    }

    /**
     * connEtoM1: (
     * (TextField1,text.textValueChanged(java.awt.event.TextEvent) -->
     * TrySorts,checkOutputEnable(Ljava.lang.String;)Z).normalResult -->
     * Checkbox8.setEnabled(Z)V)
     *
     * @param result boolean
     */
    private void connEtoM1(boolean result) {
        try {
            getShowOutputBox().setEnabled(result);
        } catch (java.lang.Throwable ivjExc) {
            handleException(ivjExc);
        }
    }

    /**
     * connEtoM3:
     * (ClearOutputButton.action.actionPerformed(java.awt.event.ActionEvent)
     * --> TextArea1.setText(Ljava.lang.String;)V)
     */
    private void connEtoM3() {
        try {
            getOutputArea().setText("");
        } catch (java.lang.Throwable ivjExc) {
            handleException(ivjExc);
        }
    }

    /**
     * connEtoM4: (
     * (NumItemsField,text.textValueChanged(java.awt.event.TextEvent)
     * --> TrySorts,checkOutputEnable(Ljava.lang.String;)Z).normalResult
     * --> ShowOutputBox.state)
     *
     * @param result boolean
     */
    private void connEtoM4(boolean result) {
        try {
            getShowOutputBox().setState(result);
        } catch (java.lang.Throwable ivjExc) {
            handleException(ivjExc);
        }
    }


    /**
     * connPtoP6SetTarget: (Checkbox7.this <-->
     * CheckboxGroup1.selectedCheckbox)
     */
    private void connPtoP6SetTarget() {
    /* Set the target from the source */
        try {
            getHeapSortBox().setCheckboxGroup(getSortTypeGroup());
        } catch (java.lang.Throwable ivjExc) {
            handleException(ivjExc);
        }
    }

    /**
     * connPtoP7SetTarget: (Checkbox6.this <-->
     * CheckboxGroup1.selectedCheckbox)
     */
    private void connPtoP7SetTarget() {
    /* Set the target from the source */
        try {
            getQuickSortBox().setCheckboxGroup(getSortTypeGroup());
        } catch (java.lang.Throwable ivjExc) {
            handleException(ivjExc);
        }
    }

    /**
     * connPtoP8SetTarget: (SortTypeGroup.this <-->
     * ArrayBubbleSortBox.checkboxGroup)
     */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connPtoP8SetTarget() {
        try {
            getBubbleSortBox().setCheckboxGroup(getSortTypeGroup());
        } catch (java.lang.Throwable ivjExc) {
            handleException(ivjExc);
        }
    }

    private void connPtoP9SetTarget() {
        try {
            getMergesortBox().setCheckboxGroup(getSortTypeGroup());
        } catch (java.lang.Throwable ivjExc) {
            handleException(ivjExc);
        }
    }


    /**
     * Create an array of Long and initialize it with random numbers
     *
     * @param size           int
     * @param maximumElement Long
     * @return Long[]
     */
    public Long[] createLongArray(int size, long maximumElement) {
        Long[] testSequence = new Long[size];
        for (int i = 0; i < size; i++) {
            testSequence[i] = (long) (Math.random() * maximumElement) + 1;
        }
        return testSequence;
    }

    /**
     * Ouput array to applet; beforeFlag switches text
     *
     * @param seq  Long[]
     * @param size int
     */
    public void doArrayOutput(Long[] seq, int size, boolean beforeFlag) {
        String outputText = "";
        if (getShowOutputBox().getState()) {
            if (beforeFlag) {
                getOutputArea().append("\nInitial array:\n");
            } else {
                getOutputArea().append("\nSorted array:\n");
            }
            for (int index = 0; index < size; index++) {
                outputText = outputText + "  " + Long.toString(seq[index]);
            }
            outputText = outputText + "\n";
            getOutputArea().append(outputText);
            getOutputArea().append("\n\n");
        }
    }

    /**
     * Call bubble sort and time it
     *
     * @param seq Long[]
     * @return java.lang.String
     */
    public String doBubbleSort(Long[] seq) {
        java.util.Date timeIn = new java.util.Date();
        new BubbleSort<>(seq);
        java.util.Date timeOut = new java.util.Date();
        long timeTaken = timeOut.getTime() - timeIn.getTime();
        return Long.toString(timeTaken);
    }

    public String doMergeSort(Long[] seq) {
        java.util.Date timeIn = new java.util.Date();
        new MergeSort<>(seq);
        java.util.Date timeOut = new java.util.Date();
        long timeTaken = timeOut.getTime() - timeIn.getTime();
        return Long.toString(timeTaken);
    }

    /**
     * Call heap sort and time it
     *
     * @param seq Long[]
     * @return java.lang.String
     */
    public String doHeapSort(Long[] seq) {
        java.util.Date timeIn = new java.util.Date();
        new HeapSortInPlace<>(seq);
        java.util.Date timeOut = new java.util.Date();
        long timeTaken = timeOut.getTime() - timeIn.getTime();
        return Long.toString(timeTaken);
    }

    /**
     * Call quick sort and time it
     *
     * @param seq Long[]
     * @return java.lang.String
     */
    public String doQuickSort(Long[] seq) {
        java.util.Date timeIn = new java.util.Date();
        new QuickSortInPlace<>(seq);
        // Sorters.quickSort( seq, 0, size - 1 );
        java.util.Date timeOut = new java.util.Date();
        long timeTaken = timeOut.getTime() - timeIn.getTime();
        return Long.toString(timeTaken);
    }


    public void doNewButton(String arraySizeText, String maxElementText) {
        int arraySize;
        long maxElement;

        if (checkFields(arraySizeText, maxElementText)) {
            arraySize = Integer.parseInt(arraySizeText);
            maxElement = Long.parseLong(maxElementText);
            d_testSequence = createLongArray(arraySize, maxElement);
        }
    }


    /**
     * Initiate and time sorts according to selcted options
     *
     * @param arraySizeText  java.lang.String
     * @param maxElementText java.lang.String
     * @param sortOption     java.awt.Checkbox
     */
    public void doStartButton(String arraySizeText, String maxElementText, Checkbox sortOption) {
        String timeTaken = "";
        int arraySize;
        long maxElement;
        if (checkFields(arraySizeText, maxElementText, sortOption)) {
            arraySize = Integer.parseInt(arraySizeText);
            maxElement = Long.parseLong(maxElementText);
            getTimeTakenField().setText("");
            if (d_testSequence == null) {
                d_testSequence = createLongArray(arraySize, maxElement);
            }
            Long testSequence[] = new Long[d_testSequence.length];
            System.arraycopy(d_testSequence, 0, testSequence, 0, d_testSequence.length);
            doArrayOutput(testSequence, arraySize, true);
            if (sortOption == getBubbleSortBox()) {
                getOutputArea().append("Starting bubble sort...\n");
                timeTaken = doBubbleSort(testSequence);
            } else if (sortOption == getHeapSortBox()) {
                getOutputArea().append("Starting heap sort...\n");
                timeTaken = doHeapSort(testSequence);
            } else if (sortOption == getQuickSortBox()) {
                getOutputArea().append("Starting quick sort...\n");
                timeTaken = doQuickSort(testSequence);
            } else if (sortOption == getMergesortBox()) {
                getOutputArea().append("Starting merge sort...\n");
                timeTaken = doMergeSort(testSequence);
            }
            getOutputArea().append("Sort complete.\n");
            getTimeTakenField().setText(timeTaken);
            doArrayOutput(testSequence, arraySize, false);
        }
    }

    /**
     * Returns information about this applet.
     *
     * @return a string of information about this applet
     */
    public String getAppletInfo() {
        return "TrySorts";
    }

    /**
     * Return the ArraySortLabel property value.
     *
     * @return java.awt.Label
     */
    private java.awt.Label getArraySortLabel() {
        if (ivjArraySortLabel == null) {
            try {
                ivjArraySortLabel = new java.awt.Label();
                ivjArraySortLabel.setName("ArraySortLabel");
                ivjArraySortLabel.setText("Integer array sorts");
                ivjArraySortLabel.setBounds(313, 15, 137, 23);
            } catch (java.lang.Throwable ivjExc) {
                handleException(ivjExc);
            }
        }
        return ivjArraySortLabel;
    }


    /**
     * Return the BubbleSortBox property value.
     *
     * @return java.awt.Checkbox
     */
    private java.awt.Checkbox getBubbleSortBox() {
        if (ivjBubbleSortBox == null) {
            try {
                ivjBubbleSortBox = new java.awt.Checkbox();
                ivjBubbleSortBox.setName("BubbleSortBox");
                ivjBubbleSortBox.setBounds(314, 120, 145, 23);
                ivjBubbleSortBox.setLabel("Bubble sort");
            } catch (java.lang.Throwable ivjExc) {
                handleException(ivjExc);
            }
        }
        return ivjBubbleSortBox;
    }

    private java.awt.Checkbox getMergesortBox() {
        if (ivjMergeSortBox == null) {
            try {
                ivjMergeSortBox = new java.awt.Checkbox();
                ivjMergeSortBox.setName("MergeSortBox");
                ivjMergeSortBox.setBounds(314, 140, 145, 23);
                ivjMergeSortBox.setLabel("Merge sort");
            } catch (java.lang.Throwable ivjExc) {
                handleException(ivjExc);
            }
        }
        return ivjMergeSortBox;
    }


    /**
     * Return the ClearOutputButton property value.
     *
     * @return java.awt.Button
     */
    private java.awt.Button getClearOutputButton() {
        if (ivjClearOutputButton == null) {
            try {
                ivjClearOutputButton = new java.awt.Button();
                ivjClearOutputButton.setName("ClearOutputButton");
                ivjClearOutputButton.setBounds(374, 462, 82, 23);
                ivjClearOutputButton.setLabel("Clear Output");
            } catch (java.lang.Throwable ivjExc) {
                handleException(ivjExc);
            }
        }
        return ivjClearOutputButton;
    }


    /**
     * Return the Checkbox3 property value.
     *
     * @return java.awt.Checkbox
     */
    private java.awt.Checkbox getHeapSortBox() {
        if (ivjHeapSortBox == null) {
            try {
                ivjHeapSortBox = new java.awt.Checkbox();
                ivjHeapSortBox.setName("HeapSortBox");
                ivjHeapSortBox.setBounds(314, 71, 180, 23);
                ivjHeapSortBox.setEnabled(true);
                ivjHeapSortBox.setLabel("Heap sort");
            } catch (java.lang.Throwable ivjExc) {
                handleException(ivjExc);
            }
        }
        return ivjHeapSortBox;
    }

    /**
     * Return the MaxElementField property value.
     *
     * @return java.awt.TextField
     */
    private java.awt.TextField getMaxElementField() {
        if (ivjMaxElementField == null) {
            try {
                ivjMaxElementField = new java.awt.TextField();
                ivjMaxElementField.setName("MaxElementField");
                ivjMaxElementField.setText("1000");
                ivjMaxElementField.setBounds(41, 44, 111, 23);
            } catch (java.lang.Throwable ivjExc) {
                handleException(ivjExc);
            }
        }
        return ivjMaxElementField;
    }

    /**
     * Return the MaxElementLabel property value.
     *
     * @return java.awt.Label
     */
    private java.awt.Label getMaxElementLabel() {
        if (ivjMaxElementLabel == null) {
            try {
                ivjMaxElementLabel = new java.awt.Label();
                ivjMaxElementLabel.setName("MaxElementLabel");
                ivjMaxElementLabel.setText("Maximum element");
                ivjMaxElementLabel.setBounds(41, 16, 109, 23);
            } catch (java.lang.Throwable ivjExc) {
                handleException(ivjExc);
            }
        }
        return ivjMaxElementLabel;
    }


    /**
     * Return the TextField1 property value.
     *
     * @return java.awt.TextField
     */
    private java.awt.TextField getNumItemsField() {
        if (ivjNumItemsField == null) {
            try {
                ivjNumItemsField = new java.awt.TextField();
                ivjNumItemsField.setName("NumItemsField");
                ivjNumItemsField.setText("100");
                ivjNumItemsField.setBounds(36, 197, 118, 23);
            } catch (java.lang.Throwable ivjExc) {
                handleException(ivjExc);
            }
        }
        return ivjNumItemsField;
    }

    /**
     * Return the Label1 property value.
     *
     * @return java.awt.Label
     */
    private java.awt.Label getNumItemsLabel() {
        if (ivjNumItemsLabel == null) {
            try {
                ivjNumItemsLabel = new java.awt.Label();
                ivjNumItemsLabel.setName("NumItemsLabel");
                ivjNumItemsLabel.setText("Number of items");
                ivjNumItemsLabel.setBounds(41, 162, 98, 23);
            } catch (java.lang.Throwable ivjExc) {
                handleException(ivjExc);
            }
        }
        return ivjNumItemsLabel;
    }

    /**
     * Return the TextArea1 property value.
     *
     * @return java.awt.TextArea
     */
    private java.awt.TextArea getOutputArea() {
        if (ivjOutputArea == null) {
            try {
                ivjOutputArea = new java.awt.TextArea();
                ivjOutputArea.setName("OutputArea");
                ivjOutputArea.setBounds(185, 188, 360, 263);
            } catch (java.lang.Throwable ivjExc) {
                handleException(ivjExc);
            }
        }
        return ivjOutputArea;
    }

    /**
     * Return the Label3 property value.
     *
     * @return java.awt.Label
     */
    private java.awt.Label getOutputLabel() {
        if (ivjOutputLabel == null) {
            try {
                ivjOutputLabel = new java.awt.Label();
                ivjOutputLabel.setName("OutputLabel");
                ivjOutputLabel.setText("Output");
                ivjOutputLabel.setBounds(190, 160, 52, 23);
            } catch (java.lang.Throwable ivjExc) {
                handleException(ivjExc);
            }
        }
        return ivjOutputLabel;
    }

    /**
     * Return the Checkbox4 property value.
     *
     * @return java.awt.Checkbox
     */
    private java.awt.Checkbox getQuickSortBox() {
        if (ivjQuickSortBox == null) {
            try {
                ivjQuickSortBox = new java.awt.Checkbox();
                ivjQuickSortBox.setName("QuickSortBox");
                ivjQuickSortBox.setBounds(314, 94, 171, 23);
                ivjQuickSortBox.setLabel("Quick sort");
            } catch (java.lang.Throwable ivjExc) {
                handleException(ivjExc);
            }
        }
        return ivjQuickSortBox;
    }


    /**
     * Return the Checkbox8 property value.
     *
     * @return java.awt.Checkbox
     */
    private java.awt.Checkbox getShowOutputBox() {
        if (ivjShowOutputBox == null) {
            try {
                ivjShowOutputBox = new java.awt.Checkbox();
                ivjShowOutputBox.setName("ShowOutputBox");
                ivjShowOutputBox.setBounds(50, 233, 91, 23);
                ivjShowOutputBox.setLabel("Show items");
            } catch (java.lang.Throwable ivjExc) {
                handleException(ivjExc);
            }
        }
        return ivjShowOutputBox;
    }

    /**
     * Return the CheckboxGroup1 property value.
     *
     * @return java.awt.CheckboxGroup
     */
    private java.awt.CheckboxGroup getSortTypeGroup() {
        if (ivjSortTypeGroup == null) {
            try {
                ivjSortTypeGroup = new java.awt.CheckboxGroup();
            } catch (java.lang.Throwable ivjExc) {
                handleException(ivjExc);
            }
        }
        return ivjSortTypeGroup;
    }

    /**
     * Return the Button1 property value.
     *
     * @return java.awt.Button
     */
    private java.awt.Button getStartButton() {
        if (ivjStartButton == null) {
            try {
                ivjStartButton = new java.awt.Button();
                ivjStartButton.setName("StartButton");
                ivjStartButton.setBounds(71, 269, 56, 23);
                ivjStartButton.setLabel("Start");
            } catch (java.lang.Throwable ivjExc) {
                handleException(ivjExc);
            }
        }
        return ivjStartButton;
    }


    private java.awt.Button getNewButton() {
        if (ivjNewButton == null) {
            try {
                ivjNewButton = new java.awt.Button();
                ivjNewButton.setName("New");
                ivjNewButton.setBounds(51, 309, 96, 23);
                ivjNewButton.setLabel("New Elements");
            } catch (java.lang.Throwable ivjExc) {
                handleException(ivjExc);
            }
        }
        return ivjNewButton;
    }


    /**
     * Return the TextField2 property value.
     *
     * @return java.awt.TextField
     */
    private java.awt.TextField getTimeTakenField() {
        if (ivjTimeTakenField == null) {
            try {
                ivjTimeTakenField = new java.awt.TextField();
                ivjTimeTakenField.setName("TimeTakenField");
                ivjTimeTakenField.setBounds(53, 389, 106, 23);
                ivjTimeTakenField.setEditable(false);
            } catch (java.lang.Throwable ivjExc) {
                handleException(ivjExc);
            }
        }
        return ivjTimeTakenField;
    }

    /**
     * Return the Label2 property value.
     *
     * @return java.awt.Label
     */
    private java.awt.Label getTimeTakenLabel() {
        if (ivjTimeTakenLabel == null) {
            try {
                ivjTimeTakenLabel = new java.awt.Label();
                ivjTimeTakenLabel.setName("TimeTakenLabel");
                ivjTimeTakenLabel.setText("Time taken (milliseconds)");
                ivjTimeTakenLabel.setBounds(29, 357, 149, 23);
            } catch (java.lang.Throwable ivjExc) {
                handleException(ivjExc);
            }
        }
        return ivjTimeTakenLabel;
    }

    /**
     * Called whenever the part throws an exception.
     *
     * @param exception java.lang.Throwable
     */
    private void handleException(java.lang.Throwable exception) {

    /* Uncomment the following lines to print uncaught exceptions to stdout */
        System.out.println("--------- UNCAUGHT EXCEPTION ---------");
        exception.printStackTrace(System.out);
    }

    /**
     * Initializes the applet.
     */
    public void init() {
        try {
            super.init();
            setName("TrySorts");
            setLayout(null);
            setSize(566, 502);
            add(getQuickSortBox(), getQuickSortBox().getName());
            add(getHeapSortBox(), getHeapSortBox().getName());
            add(getShowOutputBox(), getShowOutputBox().getName());
            add(getNumItemsField(), getNumItemsField().getName());
            add(getNumItemsLabel(), getNumItemsLabel().getName());
            add(getStartButton(), getStartButton().getName());
            add(getNewButton(), getNewButton().getName());
            add(getOutputArea(), getOutputArea().getName());
            add(getTimeTakenField(), getTimeTakenField().getName());
            add(getTimeTakenLabel(), getTimeTakenLabel().getName());
            add(getClearOutputButton(), getClearOutputButton().getName());
            add(getOutputLabel(), getOutputLabel().getName());
            add(getMaxElementField(), getMaxElementField().getName());
            add(getMaxElementLabel(), getMaxElementLabel().getName());
            add(getArraySortLabel(), getArraySortLabel().getName());
            add(getBubbleSortBox(), getBubbleSortBox().getName());
            add(getMergesortBox(), getMergesortBox().getName());
            initConnections();
        } catch (java.lang.Throwable ivjExc) {
            handleException(ivjExc);
        }
    }

    /**
     * Initializes connections
     *
     * @throws java.lang.Exception The exception description.
     */
    private void initConnections() throws java.lang.Exception {
        getNumItemsField().addTextListener(ivjEventHandler);
        getClearOutputButton().addActionListener(ivjEventHandler);
        getStartButton().addActionListener(ivjEventHandler);
        getNewButton().addActionListener(ivjEventHandler);
        connPtoP6SetTarget();
        connPtoP7SetTarget();
        connPtoP8SetTarget();
        connPtoP9SetTarget();
    }

    /**
     * Starts the applet when it is run as an application
     *
     * @param args an array of command-line arguments
     */
    public static void main(java.lang.String[] args) {
        TrySorts applet = new TrySorts();
        java.awt.Frame frame = new java.awt.Frame("Applet");

        frame.addWindowListener(applet);
        frame.add("Center", applet);
        frame.setSize(350, 250);
        frame.setVisible(true);

        applet.init();
        applet.start();
    }

    /**
     * Called when the mouse has been clicked.
     *
     * @param e the received event
     */
    public void mouseClicked(MouseEvent e) {
        // Do nothing
        // System.out.println("mouseClicked");
    }

    /**
     * Called when the mouse has entered a window.
     *
     * @param e the received event
     */
    public void mouseEntered(MouseEvent e) {
        // Do nothing
        // System.out.println("mouseEntered");
    }

    /**
     * Called when the mouse has exited a window.
     *
     * @param e the received event
     */
    public void mouseExited(MouseEvent e) {
        // Do nothing
        // System.out.println("mouseExited");
    }

    /**
     * Called when a mouse button has been pressed.
     *
     * @param e the received event
     */
    public void mousePressed(MouseEvent e) {
        // Do nothing
        // System.out.println("mousePressed");
    }

    /**
     * Called when a mouse button has been released.
     *
     * @param e the received event
     */
    public void mouseReleased(MouseEvent e) {
        // Do nothing
        // System.out.println("mouseReleased");
    }

    /**
     * Paints the applet.
     * If the applet does not need to be painted (e.g. if it is only a container for other
     * awt components) then this method can be safely removed.
     *
     * @param g the specified Graphics window
     * @see #update
     */
    public void paint(Graphics g) {
        super.paint(g);

        // insert code to paint the applet here
    }

    /**
     * Invoked when a window is activated.
     *
     * @param e the received event
     */
    public void windowActivated(WindowEvent e) {
        // Do nothing.
        // This method is required to comply with the WindowListener interface.
    }

    /**
     * Invoked when a window has been closed.
     *
     * @param e the received event
     */
    public void windowClosed(WindowEvent e) {
        // Do nothing.
        // This method is required to comply with the WindowListener interface.
    }

    /**
     * Invoked when a window is in the process of being closed.
     * The close operation can be overridden at this point.
     *
     * @param e the received event
     */
    public void windowClosing(WindowEvent e) {
        // The window is being closed.  Shut down the system.
        System.exit(0);
    }

    /**
     * Invoked when a window is deactivated.
     *
     * @param e the received event
     */
    public void windowDeactivated(WindowEvent e) {
        // Do nothing.
        // This method is required to comply with the WindowListener interface.
    }

    /**
     * Invoked when a window is de-iconified.
     *
     * @param e the received event
     */
    public void windowDeiconified(WindowEvent e) {
        // Do nothing.
        // This method is required to comply with the WindowListener interface.
    }

    /**
     * Invoked when a window is iconified.
     *
     * @param e the received event
     */
    public void windowIconified(WindowEvent e) {
        // Do nothing.
        // This method is required to comply with the WindowListener interface.
    }

    /**
     * Invoked when a window has been opened.
     *
     * @param e the received event
     */
    public void windowOpened(WindowEvent e) {
        // Do nothing.
        // This method is required to comply with the WindowListener interface.
    }

    public static boolean isNumeric(String s) {
        return isNumeric(s, 10);
    }

    public static boolean isNumeric(String s, int radix) {
        if (s.isEmpty()) {
            return false;
        }
        for (int i = 0; i < s.length(); i++) {
            if (i == 0 && s.charAt(i) == '-') {
                if (s.length() == 1) {
                    return false;
                } else {
                    continue;
                }
            }
            if (Character.digit(s.charAt(i), radix) < 0) {
                return false;
            }
        }
        return true;
    }
}
