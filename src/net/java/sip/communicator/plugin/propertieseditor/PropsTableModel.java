/*
 * Jitsi, the OpenSource Java VoIP and Instant Messaging client.
 * 
 * Distributable under LGPL license. See terms of license at gnu.org.
 */
package net.java.sip.communicator.plugin.propertieseditor;

import java.beans.*;

import javax.swing.table.*;

import org.jitsi.service.configuration.*;

/**
 *
 * @author Marin Dzhigarov
 */
public class PropsTableModel
    extends DefaultTableModel
    implements PropertyChangeListener
{
    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 1L;

    private final ConfigurationService confService
        = PropertiesEditorActivator.getConfigurationService();

    /**
     * Creates an instance of <tt>PropsTableModel</tt>.
     */
    public PropsTableModel(Object[][] data, String[] columnNames)
    {
        super(data, columnNames);

        confService.addPropertyChangeListener(this);
    }

    /**
     * Listens for <tt>PropertyChangeEvent</tt>'s in the
     * <tt>ConfigurationService</tt> and updates the model if new property is
     * added.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        // If new property was added
        if (evt.getOldValue() == null)
            addRow(new Object[] {evt.getPropertyName(), evt.getNewValue()});
    }

    /**
     * Returns true if the given cell is editable. Editable cells in this model
     * are cells with column index 1.
     *
     * @param rowIndex The row index.
     * @param columnIndex The column index.
     * @return <tt>true</tt> if the column index is 1
     */
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        return columnIndex == 1;
    }

    /**
     * Sets aValue at a cell with coordinates [<tt>rowIndex</tt>,
     * <tt>columnIndex</tt>].
     *
     * @param aValue The given value.
     * @param rowIndex The row index that the value will be set to.
     * @param columnIndex The column index that the value will be set to.
     */
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
        String property = (String)getValueAt(rowIndex, 0);

        confService.setProperty(property, aValue);
        super.setValueAt(aValue, rowIndex, columnIndex);
    }
}
