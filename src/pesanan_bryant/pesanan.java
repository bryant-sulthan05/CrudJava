/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pesanan_bryant;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Administrator
 */
public class pesanan extends javax.swing.JFrame {

    Connection conn = null;
    ResultSet foodResult = null;
    ResultSet drinkResult = null;    
    ResultSet transactionResult = null;

    PreparedStatement foodPrepState = null;
    PreparedStatement drinkPrepState = null;    
    PreparedStatement transactionPrepState = null;

    Statement newFoodState;
    Statement newDrinkState;
    
    List<String> foodNameList = new ArrayList<String>();
    List<String> foodPriceList = new ArrayList<String>();
    
    List<String> drinkNameList = new ArrayList<String>();
    List<String> drinkPriceList = new ArrayList<String>();

    /**
     * Creates new form pesanan
     */
    public pesanan() {
        initComponents();
        connection();
        displayItem();
        displayTransaction();
    }
    
    void connection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost/pesanan_bryant";
            String user = "root";
            String pass = "";
            
            conn = DriverManager.getConnection(url, user, pass);
            
            newFoodState = conn.createStatement(foodResult.TYPE_SCROLL_SENSITIVE, foodResult.CONCUR_UPDATABLE);
            newDrinkState = conn.createStatement(drinkResult.TYPE_SCROLL_SENSITIVE, drinkResult.CONCUR_UPDATABLE);
            
            foodResult = newFoodState.executeQuery("SELECT * FROM makanan");
            drinkResult = newDrinkState.executeQuery("SELECT * FROM drink");
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, e);
            System.exit(0);
        }
    }

    void displayItem() {
        try {
            String foodQuery = "SELECT * FROM makanan";
            String drinkQuery = "SELECT * FROM drink";

            foodPrepState = conn.prepareStatement(foodQuery);
            drinkPrepState = conn.prepareStatement(drinkQuery);

            foodResult = foodPrepState.executeQuery();
            drinkResult = drinkPrepState.executeQuery();
            
            List<ComboItem> foodList = new ArrayList<ComboItem>();
            List<ComboItem> drinkList = new ArrayList<ComboItem>();
            
            while (foodResult.next()) {
                String foodId = foodResult.getString("food_id");
                String foodName = foodResult.getString("food_name");
                String foodPrice = foodResult.getString("price");
                String foodLabel = foodName + " (Rp. " + foodPrice + ")";
                foodNameList.add(foodName);
                foodPriceList.add(foodPrice);
                foodList.add(new ComboItem(foodName, foodId));
            }
            
            while (drinkResult.next()) {
                String drinkId = drinkResult.getString("drink_id");
                String drinkName = drinkResult.getString("drink_name");
                String drinkPrice = drinkResult.getString("price");
                String drinkLabel = drinkName + " (Rp. " + drinkPrice + ")";
                drinkNameList.add(drinkName);
                drinkPriceList.add(drinkPrice);
                drinkList.add(new ComboItem(drinkName, drinkId));
            }
            
            ComboItem[] cvtFoodList = new ComboItem[ foodList.size() ];
            foodList.toArray(cvtFoodList);
            
            ComboItem[] cvtDrinkList = new ComboItem[ drinkList.size() ];
            drinkList.toArray(cvtDrinkList);
            
            foodComboBox.setModel(new DefaultComboBoxModel<ComboItem>(cvtFoodList));
            drinkComboBox.setModel(new DefaultComboBoxModel<ComboItem>(cvtDrinkList));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            System.exit(0);
        }
    }
    
    void displayTransaction() {
        try {
            String query = "SELECT transaction_id, food_name, makanan.price, food_qty, drink_name, drink.price, drink_qty, total FROM transaction JOIN makanan USING (food_id) JOIN drink USING (drink_id)";

            transactionPrepState = conn.prepareStatement(query);

            transactionResult = transactionPrepState.executeQuery();
            
            transactionTable.setModel(DbUtils.resultSetToTableModel(transactionResult));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            System.exit(0);
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        drinkComboBox = new javax.swing.JComboBox<>();
        foodComboBox = new javax.swing.JComboBox<>();
        foodQuantity = new javax.swing.JSpinner();
        drinkQuantity = new javax.swing.JSpinner();
        orderButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        transactionTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Transaksi");

        jLabel2.setText("Makanan");

        jLabel3.setText("Minuman");

        drinkComboBox.setModel(new javax.swing.DefaultComboBoxModel<>());

        foodComboBox.setModel(new javax.swing.DefaultComboBoxModel<>());

        orderButton.setText("Pesan");
        orderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                orderButtonActionPerformed(evt);
            }
        });

        transactionTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID Transaksi", "Nama Makanan", "Nama Minuman", "Jumlah Makanan", "Jumlah Minuman", "Total"
            }
        ));
        jScrollPane2.setViewportView(transactionTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(orderButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 517, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2))
                                .addGap(27, 27, 27)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(foodComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(foodQuantity))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(drinkComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(drinkQuantity)))))))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(235, 235, 235))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(foodComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(foodQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(drinkComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(drinkQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addComponent(orderButton)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void orderButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_orderButtonActionPerformed
        // TODO add your handling code here:
        try {                
            connection();
            String foodQuery = "SELECT * FROM makanan WHERE food_name = '" + foodComboBox.getSelectedItem() + "'";
            String drinkQuery = "SELECT * FROM drink WHERE drink_name = '" + drinkComboBox.getSelectedItem() + "'";
            int foodQt = Integer.parseInt(foodQuantity.getValue().toString());
            int drinkQt = Integer.parseInt(drinkQuantity.getValue().toString());
            
            foodPrepState = conn.prepareStatement(foodQuery);
            drinkPrepState = conn.prepareStatement(drinkQuery);
        
            foodResult = foodPrepState.executeQuery();
            drinkResult = drinkPrepState.executeQuery();
            
            String foodId = null, foodName, drinkId = null, drinkName;
            int foodPrice = 0, drinkPrice = 0;
            
            if (foodResult.next()) {
                foodId = foodResult.getString("food_id");
                foodName = foodResult.getString("food_name");
                foodPrice = Integer.parseInt(foodResult.getString("price"));
            }
            
            if (drinkResult.next()) {
                drinkId = drinkResult.getString("drink_id");
                drinkName = drinkResult.getString("drink_name");
                drinkPrice = Integer.parseInt(drinkResult.getString("price"));
            }
            
            int resultFoodCalc = foodPrice * foodQt;
            int resultDrinkCalc = drinkPrice * drinkQt;
            int totalResult = resultFoodCalc + resultDrinkCalc;

            try{
                newFoodState = conn.createStatement();
                String query = "INSERT INTO transaction VALUE(NULL,'"+foodId+"','"+drinkId+"','"+foodQt+"','"+drinkQt+"','"+totalResult+"')";
                newFoodState.executeUpdate(query);
                displayTransaction();
                newFoodState.close();
                conn.close();
                JOptionPane.showMessageDialog(null, "Transaksi berhasil!");
            } catch(Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
    }//GEN-LAST:event_orderButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(pesanan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(pesanan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(pesanan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(pesanan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new pesanan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<ComboItem> drinkComboBox;
    private javax.swing.JSpinner drinkQuantity;
    private javax.swing.JComboBox<ComboItem> foodComboBox;
    private javax.swing.JSpinner foodQuantity;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton orderButton;
    private javax.swing.JTable transactionTable;
    // End of variables declaration//GEN-END:variables
}

class ComboItem {
    private String label;
    private String value;
    
    public ComboItem(String label, String value) {
        this.label = label;
        this.value = value;
    }
    
    public String getLabel() {
        return this.label;
    }
    
    public String getValue() {
        return this.value;
    }
    
    @Override
    public String toString() {
        return this.label;
    }
}