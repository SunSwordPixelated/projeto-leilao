import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ProdutosDAO {

    private Connection conn;

    
    private Connection getConnection() {
        if (conn == null) {
            conn = new conectaDAO().connectDB();
        }
        return conn;
    }

    public void cadastrarProduto(ProdutosDTO produto) {
        String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";
        PreparedStatement prep = null;
        try {
            conn = getConnection();
            prep = conn.prepareStatement(sql);
            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());
            prep.executeUpdate();
            JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar produto: " + e.getMessage());
        } finally {
            try {
                if (prep != null) prep.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void venderProduto(int id) {
        String sql = "UPDATE produtos SET status = 'Vendido' WHERE id = ?";
        PreparedStatement prep = null;
    try {
        conn = getConnection();
        prep = conn.prepareStatement(sql);
        prep.setInt(1, id);
        int rowsAffected = prep.executeUpdate();
        
        if (rowsAffected > 0) {
            JOptionPane.showMessageDialog(null, "Produto vendido com sucesso!");
        } else {
            JOptionPane.showMessageDialog(null, "Produto não encontrado!");
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Erro ao vender produto: " + e.getMessage());
    } finally {
        try {
            if (prep != null) prep.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    }

    public ArrayList<ProdutosDTO> listarProdutos() {
        ArrayList<ProdutosDTO> listagem = new ArrayList<>();
        String sql = "SELECT id, nome, valor, status FROM produtos";
        PreparedStatement prep = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            prep = conn.prepareStatement(sql);
            rs = prep.executeQuery();
            while (rs.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setValor(rs.getInt("valor"));
                produto.setStatus(rs.getString("status"));
                listagem.add(produto);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar produtos: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (prep != null) prep.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return listagem;
    }
    
    public ArrayList<ProdutosDTO> listarProdutosVendidos() {
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    String sql = "SELECT id, nome, valor, status FROM produtos WHERE status = 'Vendido'";
    PreparedStatement prep = null;
    ResultSet rs = null;
    try {
        conn = getConnection();
        prep = conn.prepareStatement(sql);
        rs = prep.executeQuery();
        while (rs.next()) {
            ProdutosDTO produto = new ProdutosDTO();
            produto.setId(rs.getInt("id"));
            produto.setNome(rs.getString("nome"));
            produto.setValor(rs.getInt("valor"));
            produto.setStatus(rs.getString("status"));
            listagem.add(produto);
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Erro ao listar produtos vendidos: " + e.getMessage());
    } finally {
        try {
            if (rs != null) rs.close();
            if (prep != null) prep.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return listagem;
}
}
//