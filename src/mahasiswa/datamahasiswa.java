package mahasiswa;


import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel; 

public class datamahasiswa extends javax.swing.JFrame {
    private Connection con;
    private Statement stat;
    private ResultSet res;
    
    String header [] ={"Npm","Nama,”Jenis Kelamin","Program Studi","Alamat"};

    Statement st;
    koneksi kon=new koneksi();
    private DefaultTableModel tabMode;
    String QUERY; 
    
    
    private void koneksi(){
        try {
        Class.forName("com.mysql.jdbc.Driver");
        con=DriverManager.getConnection("jdbc:mysql://localhost/kampus", "root", "");
        stat=con.createStatement();
        } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void tabel(){
        DefaultTableModel tb= new DefaultTableModel();
        // Memberi nama pada setiap kolom tabel
        tb.addColumn("NPM");
        tb.addColumn("Nama");
        tb.addColumn("Jenis Kelamin");
        tb.addColumn("Prodi");
        tb.addColumn("Alamat");
        jTable1.setModel(tb);
        try{
        // Mengambil data dari database
        res=stat.executeQuery("select * from mahasiswa");

        while (res.next())
        {
            // Mengambil data dari database berdasarkan nama kolom pada tabel
            // Lalu di tampilkan ke dalam JTable
            tb.addRow(new Object[]{
            res.getString("Npm"),
            res.getString("nama"),
            res.getString("jenkel"),
            res.getString("prodi"),
            res.getString("alamat")
            });
        }

        }catch (Exception e){
        }
}
    
    public datamahasiswa() {
        initComponents();
        koneksi();
        tabel();
        tabMode = (DefaultTableModel) jTable1.getModel();
    }
    
    private void clear(){
        txtNpm.setText("");
        txtNama.setText("");
        txtAlamat.setText("");
        txtNpm.requestFocus();
    }
    
    private void removeTable(){
        try {
            for (int t=tabMode.getRowCount();t>0;t--)
            {
                tabMode.removeRow(0);}
            } catch (Exception ex) {
        System.out.println(ex);
        }
    }

    private void tampilDataTabel(){
    removeTable();
    tabel();
    tabMode = (DefaultTableModel) jTable1.getModel();
} 
    
    private void Search() {
        if (JComboBoxCari.getSelectedItem() == null || txtCari.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Pilih kolom pencarian dan isi kata kunci pencarian.");
        return;
        }
        removeTable();
        try {
            con = kon.open();
            String selectedColumn = JComboBoxCari.getSelectedItem().toString();
            QUERY = "SELECT * FROM mahasiswa WHERE " + selectedColumn + " LIKE ?";
            PreparedStatement pst = con.prepareStatement(QUERY);
            pst.setString(1, "%" + txtCari.getText() + "%");

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String snpm = rs.getString("npm");
                String snama = rs.getString("nama");
                String sjenkel = rs.getString("jenkel");
                String sprodi = rs.getString("prodi");
                String salamat = rs.getString("alamat");
                String data[] = { snpm, snama, sjenkel, sprodi, salamat };
                tabMode.addRow(data);
            }
        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(null, "Data gagal masuk tabel: " + sqle.getMessage());
        }
    }
    
    private void input(){
        String kelamin="";
        if (JRadioButtonLaki.isSelected()){
            kelamin ="Laki-laki";
        }
        else if (JRadioButtonPerempuan.isSelected()){
            kelamin ="Perempuan";
        }
        try {
            kon.open();
            kon.QUERY("insert into mahasiswa (npm,nama,jenkel,prodi,alamat) values ('"+txtNpm.getText()+"','"+
            txtNama.getText()+"','"+
            kelamin+" ','"+
            JComboBoxProgram.getSelectedItem()+"','"+
            txtAlamat.getText()+"')","simpan");
            clear();
            tampilDataTabel();
        } catch (Exception sqle) {
            tampilDataTabel();
        }
    }
    
    private void update(){
        String kelamin="";
        if (JRadioButtonLaki.isSelected()){
            kelamin ="Laki-laki";
        }
        else if (JRadioButtonPerempuan.isSelected()){
            kelamin ="Perempuan";
        }
        try {
            kon.QUERY("update mahasiswa set nama='"+txtNama.getText()+"',jenkel='"+
            kelamin+"',prodi='"+
            JComboBoxProgram.getSelectedItem()+"',alamat='"+
            txtAlamat.getText()+"'where npm='"+
            txtNpm.getText()+"' "," Ubah");
            tampilDataTabel();
            clear();

        } catch (Exception sqle) {
            JOptionPane.showMessageDialog(rootPane, "data gagal diubah"+sqle.getMessage());
        }
    }
    
    private void ShowDataTabelKeText(){
        txtNpm.setText(String.valueOf(jTable1.getValueAt(jTable1.getSelectedRow(), 0)));
        txtNama.setText(String.valueOf(jTable1.getValueAt(jTable1.getSelectedRow(),1)));
        JComboBoxProgram.setSelectedItem(String.valueOf(jTable1.getValueAt(jTable1.getSelectedRow(), 3)));
        txtAlamat.setText(String.valueOf(jTable1.getValueAt(jTable1.getSelectedRow(), 4)));
        txtNpm.setEditable(true);
        txtNama.setEditable(true);
        JComboBoxProgram.setEditable(true);
        txtAlamat.setEditable(true);
    }
    
    private void Delete(){
        if (JOptionPane.showConfirmDialog (this,"Apakah Anda Yakin Akan Menghapus Data Ini???","Konfirmasi", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)== JOptionPane.YES_OPTION)
        {
            try {
            kon.QUERY("delete from mahasiswa where npm='"+txtNpm.getText()+"' ","hapus");
            tampilDataTabel();
            clear();
        } catch (Exception sqle) {
            JOptionPane.showMessageDialog(rootPane,"data gagal dihapus"+sqle.getMessage());
            }
        }
    } 


    
   
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        txtNpm = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtNama = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        JRadioButtonLaki = new javax.swing.JRadioButton();
        JRadioButtonPerempuan = new javax.swing.JRadioButton();
        jLabel4 = new javax.swing.JLabel();
        JComboBoxProgram = new javax.swing.JComboBox<>();
        JComboBoxCari = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        JButtonTampil = new javax.swing.JButton();
        JButtonInput = new javax.swing.JButton();
        JButtonUbah = new javax.swing.JButton();
        JButtonDelete = new javax.swing.JButton();
        JButtonClear = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        txtCari = new javax.swing.JTextField();
        JButtonCari = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAlamat = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("DATA MAHASISWA");
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);

        jLabel1.setText("Npm");

        jLabel2.setText("Nama");

        jLabel3.setText("Jenis Kelamin");

        buttonGroup1.add(JRadioButtonLaki);
        JRadioButtonLaki.setText("Laki - Laki");
        JRadioButtonLaki.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JRadioButtonLakiActionPerformed(evt);
            }
        });

        buttonGroup1.add(JRadioButtonPerempuan);
        JRadioButtonPerempuan.setText("Perempuan");

        jLabel4.setText("Program Studi");

        JComboBoxProgram.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Matematika", "Sejarah", "Novelis", "Programmer" }));
        JComboBoxProgram.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JComboBoxProgramActionPerformed(evt);
            }
        });

        JComboBoxCari.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Npm", "Nama", "Program Studi", "Alamat" }));

        jLabel5.setText("Alamat");

        JButtonTampil.setText("Tampil");
        JButtonTampil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JButtonTampilActionPerformed(evt);
            }
        });

        JButtonInput.setText("Input");
        JButtonInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JButtonInputActionPerformed(evt);
            }
        });

        JButtonUbah.setText("Ubah");
        JButtonUbah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JButtonUbahActionPerformed(evt);
            }
        });

        JButtonDelete.setText("Delete");
        JButtonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JButtonDeleteActionPerformed(evt);
            }
        });

        JButtonClear.setText("Clear");
        JButtonClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JButtonClearActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"am", "ma", "ma", "ma", "n afszb"},
                {null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5"
            }
        ));
        jTable1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTable1FocusGained(evt);
            }
        });
        jScrollPane2.setViewportView(jTable1);

        jLabel6.setText("Cari");

        txtCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCariActionPerformed(evt);
            }
        });

        JButtonCari.setText("Cari");
        JButtonCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JButtonCariActionPerformed(evt);
            }
        });

        txtAlamat.setColumns(20);
        txtAlamat.setRows(5);
        jScrollPane1.setViewportView(txtAlamat);

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel7.setText("CRUD MYSQL DATA MAHASISWA");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(308, 308, 308)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(JButtonTampil)
                        .addGap(18, 18, 18)
                        .addComponent(JButtonInput, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(JButtonUbah, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(JButtonDelete)
                        .addGap(18, 18, 18)
                        .addComponent(JButtonClear)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel3))
                                .addGap(35, 35, 35)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtNpm)
                                            .addComponent(txtNama, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(JComboBoxProgram, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addComponent(JRadioButtonLaki)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(JRadioButtonPerempuan)))
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(42, 42, 42)
                                                .addComponent(jLabel6)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(JComboBoxCari, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(101, 101, 101)
                                                .addComponent(JButtonCari, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addComponent(jScrollPane1)))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(321, 321, 321))))
            .addGroup(layout.createSequentialGroup()
                .addGap(267, 267, 267)
                .addComponent(jLabel7)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtNpm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(JRadioButtonPerempuan)
                            .addComponent(JRadioButtonLaki)
                            .addComponent(jLabel3)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JComboBoxCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JButtonCari)))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(JComboBoxProgram, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(JButtonClear)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(JButtonTampil)
                        .addComponent(JButtonInput)
                        .addComponent(JButtonUbah)
                        .addComponent(JButtonDelete)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void JRadioButtonLakiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JRadioButtonLakiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JRadioButtonLakiActionPerformed

    private void JComboBoxProgramActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JComboBoxProgramActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JComboBoxProgramActionPerformed

    private void txtCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCariActionPerformed

    private void JButtonTampilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JButtonTampilActionPerformed
        // TODO add your handling code here:
        tampilDataTabel();
    }//GEN-LAST:event_JButtonTampilActionPerformed

    private void JButtonInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JButtonInputActionPerformed
        // TODO add your handling code here:
        input();
    }//GEN-LAST:event_JButtonInputActionPerformed

    private void jTable1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTable1FocusGained
        // TODO add your handling code here:
        ShowDataTabelKeText();
    }//GEN-LAST:event_jTable1FocusGained

    private void JButtonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JButtonDeleteActionPerformed
        // TODO add your handling code here:
        Delete();
    }//GEN-LAST:event_JButtonDeleteActionPerformed

    private void JButtonClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JButtonClearActionPerformed
        // TODO add your handling code here:
        clear();
    }//GEN-LAST:event_JButtonClearActionPerformed

    private void JButtonUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JButtonUbahActionPerformed
        // TODO add your handling code here:
        update();
    }//GEN-LAST:event_JButtonUbahActionPerformed

    private void JButtonCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JButtonCariActionPerformed
        // TODO add your handling code here:
        Search();
    }//GEN-LAST:event_JButtonCariActionPerformed

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
            java.util.logging.Logger.getLogger(datamahasiswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(datamahasiswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(datamahasiswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(datamahasiswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new datamahasiswa().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton JButtonCari;
    private javax.swing.JButton JButtonClear;
    private javax.swing.JButton JButtonDelete;
    private javax.swing.JButton JButtonInput;
    private javax.swing.JButton JButtonTampil;
    private javax.swing.JButton JButtonUbah;
    private javax.swing.JComboBox<String> JComboBoxCari;
    private javax.swing.JComboBox<String> JComboBoxProgram;
    private javax.swing.JRadioButton JRadioButtonLaki;
    private javax.swing.JRadioButton JRadioButtonPerempuan;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea txtAlamat;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtNpm;
    // End of variables declaration//GEN-END:variables
}
