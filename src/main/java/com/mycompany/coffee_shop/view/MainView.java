package com.mycompany.coffee_shop.view;

import com.mycompany.coffee_shop.controller.ItemController;
import com.mycompany.coffee_shop.model.Item;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.List;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class MainView extends JFrame {

    private JTextField campoId, campoNome, campoQtd, campoFoto, campoValor;
    private JLabel labelFoto;
    private JTable tabela;
    private DefaultTableModel tableModel;
    private JTabbedPane tabbedPane;
    private ItemController controller;
    private String idEmEdicao = null;

    public MainView() {
        controller = ItemController.getInstance();
        initComponents();
        atualizarTabela();
    }

    private void initComponents() {
        setTitle("Coffee Shop CRUD");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();

        // Painel de cadastro
        JPanel painelCadastro = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        campoId = new JTextField(20);
        campoNome = new JTextField(20);
        campoQtd = new JTextField(20);
        campoValor = new JTextField(20);
        campoFoto = new JTextField(20);
        JButton btnSalvar = new JButton("Salvar");
        JButton btnLimpar = new JButton("Limpar");

        gbc.gridx = 0; gbc.gridy = 0;
        painelCadastro.add(new JLabel("Codigo:"), gbc);
        gbc.gridx = 1;
        painelCadastro.add(campoId, gbc);

        gbc.gridx = 0; gbc.gridy++;
        painelCadastro.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        painelCadastro.add(campoNome, gbc);

        gbc.gridx = 0; gbc.gridy++;
        painelCadastro.add(new JLabel("Quantidade:"), gbc);
        gbc.gridx = 1;
        painelCadastro.add(campoQtd, gbc);

        gbc.gridx = 0; gbc.gridy++;
        painelCadastro.add(new JLabel("Valor:"), gbc);
        gbc.gridx = 1;
        painelCadastro.add(campoValor, gbc);

        gbc.gridx = 0; gbc.gridy++;
        painelCadastro.add(new JLabel("Caminho da Foto:"), gbc);
        gbc.gridx = 1;
        painelCadastro.add(campoFoto, gbc);

        gbc.gridx = 0; gbc.gridy++;
        painelCadastro.add(btnSalvar, gbc);
        gbc.gridx = 1;
        painelCadastro.add(btnLimpar, gbc);

        btnSalvar.addActionListener(e -> salvarItem());
        btnLimpar.addActionListener(e -> limparCampos());

        tabbedPane.addTab("Cadastro", painelCadastro);

        JPanel painelItens = new JPanel(new BorderLayout());
        tableModel = new DefaultTableModel(new String[]{"Codigo Item", "Nome", "Qtd", "Valor"}, 0);
        tabela = new JTable(tableModel);
        JScrollPane scroll = new JScrollPane(tabela);
        
        
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(tableModel);
        tabela.setRowSorter(sorter);

        JPanel painelBotoes = new JPanel();
        JButton btnEditar = new JButton("Editar");
        JButton btnExcluir = new JButton("Excluir");
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnExcluir);

        painelItens.add(scroll, BorderLayout.CENTER);
        painelItens.add(painelBotoes, BorderLayout.SOUTH);

        labelFoto = new JLabel();
        labelFoto.setHorizontalAlignment(JLabel.CENTER);
        labelFoto.setPreferredSize(new Dimension(200, 200));
        painelItens.add(labelFoto, BorderLayout.EAST);

        tabela.getSelectionModel().addListSelectionListener(e -> mostrarFotoSelecionada());
        btnEditar.addActionListener(e -> editarItem());
        btnExcluir.addActionListener(e -> excluirItem());

        tabbedPane.addTab("Itens Cadastrados", painelItens);
        add(tabbedPane);
    }

    /**
    * Salva um novo item ou atualiza um item existente.
    * 
    * Realiza validações de campos obrigatórios, verifica se o ID já existe ao adicionar,
    * e exibe mensagens ao usuário sobre o status da operação.
    */
   private void salvarItem() {
       try {
           String id = campoId.getText();
           String nome = campoNome.getText();
           int quantidade = Integer.parseInt(campoQtd.getText());
           double valor = Double.parseDouble(campoValor.getText());
           String foto = campoFoto.getText();

           if (id.isEmpty() || nome.isEmpty()) {
               JOptionPane.showMessageDialog(this, "ID e Nome são obrigatórios.");
               return;
           }

           Item novoItem = new Item(id, nome, quantidade, valor, foto);

           if (idEmEdicao != null) {
               controller.atualizarItem(novoItem);
               JOptionPane.showMessageDialog(this, "Item atualizado com sucesso!");
               idEmEdicao = null;
           } else {
               if (controller.idExiste(id)) {
                   JOptionPane.showMessageDialog(this, "Já existe um item com este ID!", "Erro", JOptionPane.ERROR_MESSAGE);
                   return;
               }
               controller.adicionarItem(novoItem);
               JOptionPane.showMessageDialog(this, "Item salvo com sucesso!");
           }

           atualizarTabela();
           limparCampos();
           tabbedPane.setSelectedIndex(1); // Volta para aba de listagem

       } catch (NumberFormatException ex) {
           JOptionPane.showMessageDialog(this, "Quantidade e Valor devem ser números válidos.", "Erro", JOptionPane.ERROR_MESSAGE);
       }
   }



    private void limparCampos() {
        campoId.setText("");
        campoId.setEnabled(true);
        campoNome.setText("");
        campoQtd.setText("");
        campoValor.setText("");
        campoFoto.setText("");
        idEmEdicao = null;
    }

    private void atualizarTabela() {
        tableModel.setRowCount(0);
        List<Item> itens = controller.listarItens();
        for (Item i : itens) {
            tableModel.addRow(new Object[]{
                    i.getIdItem(),
                    i.getNome(),
                    i.getQuantidade(),
                    i.getValor()
            });
        }
    }

    private void mostrarFotoSelecionada() {
        int row = tabela.getSelectedRow();
        if (row != -1) {
            String id = (String) tabela.getValueAt(row, 0);
            for (Item i : controller.listarItens()) {
                if (id != null && id.equals(i.getIdItem())) {
                    String caminho = i.getFoto();
                    if (caminho != null && !caminho.isEmpty()) {
                        ImageIcon icon = new ImageIcon(caminho);
                        Image img = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                        labelFoto.setIcon(new ImageIcon(img));
                    } else {
                        labelFoto.setIcon(null);
                    }
                    break;
                }
            }
        }
    }

    private void editarItem() {
        int row = tabela.getSelectedRow();
        if (row != -1) {
            String id = (String) tabela.getValueAt(row, 0);
            for (Item i : controller.listarItens()) {
                if (id.equals(i.getIdItem())) {
                    campoId.setText(i.getIdItem());
                    campoId.setEnabled(false);
                    campoNome.setText(i.getNome());
                    campoQtd.setText(String.valueOf(i.getQuantidade()));
                    campoValor.setText(String.valueOf(i.getValor()));
                    campoFoto.setText(i.getFoto());

                    idEmEdicao = i.getIdItem();
                    tabbedPane.setSelectedIndex(0);
                    break;
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um item para editar.");
        }
    }

    private void excluirItem() {
        int row = tabela.getSelectedRow();
        if (row != -1) {
            int confirm = JOptionPane.showConfirmDialog(this, "Deseja excluir este item?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                String id = (String) tabela.getValueAt(row, 0);
                controller.removerItem(id);
                atualizarTabela();
                labelFoto.setIcon(null);
                JOptionPane.showMessageDialog(this, "Item removido com sucesso!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um item para excluir.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainView().setVisible(true));
    }
}
