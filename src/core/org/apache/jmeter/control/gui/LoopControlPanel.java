package org.apache.jmeter.control.gui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.gui.util.FocusRequester;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.gui.layout.VerticalLayout;

/****************************************
 * Title: JMeter Description: Copyright: Copyright (c) 2000 Company: Apache
 *
 *@author    Michael Stover
 *@created   $Date$
 *@version   1.0
 ***************************************/

public class LoopControlPanel extends AbstractControllerGui implements ActionListener
{

    JCheckBox infinite;
    JTextField loops;

    private boolean displayName = true;
    private static String INFINITE = "Infinite Field";
    private static String LOOPS = "Loops Field";

    /****************************************
     * !ToDo (Constructor description)
     ***************************************/
    public LoopControlPanel()
    {
        this(true);
    }

    /****************************************
     * !ToDo (Constructor description)
     *
     *@param displayName  !ToDo (Parameter description)
     ***************************************/
    public LoopControlPanel(boolean displayName)
    {
        this.displayName = displayName;
        init();
        setState(1);
    }

    /****************************************
     * !ToDo (Method description)
     *
     *@param element  !ToDo (Parameter description)
     ***************************************/
    public void configure(TestElement element)
    {
        super.configure(element);
        if (element instanceof LoopController)
        {
            setState(((LoopController) element).getLoopString());
        }
        else
        {
            setState(1);
        }
    }

    /****************************************
     * !ToDo (Method description)
     *
     *@return   !ToDo (Return description)
     ***************************************/
    public TestElement createTestElement()
    {
        LoopController lc = new LoopController();
        modifyTestElement(lc);
        return lc;
    }

    /**
         * Modifies a given TestElement to mirror the data in the gui components.
         * @see org.apache.jmeter.gui.JMeterGUIComponent#modifyTestElement(TestElement)
         */
    public void modifyTestElement(TestElement lc)
    {
        configureTestElement(lc);
        if (lc instanceof LoopController)
        {
            if (loops.getText().length() > 0)
            {
                ((LoopController) lc).setLoops(loops.getText());
            }
            else
            {
                ((LoopController) lc).setLoops(-1);
            }
        }
    }

    /****************************************
     * !ToDo (Method description)
     *
     *@param event  !ToDo (Parameter description)
     ***************************************/
    public void actionPerformed(ActionEvent event)
    {
        if (infinite.isSelected())
        {
            loops.setText("");
            loops.setEnabled(false);
        }
        else
        {
            loops.setEnabled(true);
            new FocusRequester(loops);
        }
    }

    /****************************************
     * !ToDoo (Method description)
     *
     *@return   !ToDo (Return description)
     ***************************************/
    public String getStaticLabel()
    {
        return JMeterUtils.getResString("loop_controller_title");
    }

    private void init()
    {
        // The Loop Controller panel can be displayed standalone or inside another panel.
        // For standalone, we want to display the TITLE, NAME, etc. (everything). However,
        // if we want to display it within another panel, we just display the Loop Count
        // fields (not the TITLE and NAME).

        // Standalone
        if (displayName)
        {
            setLayout(new VerticalLayout(5, VerticalLayout.LEFT, VerticalLayout.TOP));
            setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
            add(makeTitlePanel());
            add(createLoopCountPanel());
        }

        // Embedded
        else
        {
            this.add(createLoopCountPanel());
        }
    }

    private JPanel createLoopCountPanel()
    {
        JPanel loopPanel = new JPanel();

        // LOOP LABEL
        JLabel loopsLabel = new JLabel(JMeterUtils.getResString("iterator_num"));
        loopPanel.add(loopsLabel);

        // TEXT FIELD
        loops = new JTextField(5);
        loopPanel.add(loops);
        loops.setName(LOOPS);
        loops.setText("1");

        // FOREVER CHECKBOX
        infinite = new JCheckBox(JMeterUtils.getResString("infinite"));
        infinite.setActionCommand(INFINITE);
        infinite.addActionListener(this);
        loopPanel.add(infinite);

        return loopPanel;
    }
    
    private void setState(String loopCount)
    {
        loops.setText(loopCount);
        infinite.setSelected(false);
        loops.setEnabled(true);
    }

    private void setState(int loopCount)
    {
        if (loopCount <= -1)
        {
            infinite.setSelected(true);
            loops.setEnabled(false);
            loops.setText("");
        }
        else
        {
            infinite.setSelected(false);
            loops.setEnabled(true);
            loops.setText("" + loopCount);
        }
    }
}
