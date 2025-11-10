// app.js

console.log("O JavaScript está sendo carregado com sucesso!");


const API_BASE_URL = 'http://localhost:8080/usuarios';
const usuariosTableBody = document.querySelector('#usuariosTable tbody');
const usuarioForm = document.getElementById('usuarioForm');
const usuarioIdInput = document.getElementById('usuarioId');
const nomeInput = document.getElementById('nome');
const emailInput = document.getElementById('email');
const submitButton = document.getElementById('submitButton');
const cancelButton = document.getElementById('cancelButton');
const loadingMessage = document.getElementById('loadingMessage');

// --- Funções de Ajuda ---

// 1. Limpar e preencher o formulário
const clearForm = () => {
    usuarioIdInput.value = '';
    nomeInput.value = '';
    emailInput.value = '';
    submitButton.textContent = 'Cadastrar';
    cancelButton.style.display = 'none';
};

// 2. Criar uma linha da tabela
const createTableRow = (usuario) => {
    const row = usuariosTableBody.insertRow();
    row.innerHTML = `
        <td>${usuario.id}</td>
        <td>${usuario.nome}</td>
        <td>${usuario.email}</td>
        <td>
            <button class="action-button edit-btn" data-id="${usuario.id}">Editar</button>
            <button class="action-button delete-btn" data-id="${usuario.id}">Excluir</button>
        </td>
    `;
};

// --- Funções do CRUD (Comunicação com a API) ---

// READ: Listar todos os usuários
const listarUsuarios = async () => {
    loadingMessage.textContent = 'Carregando usuários...';
    usuariosTableBody.innerHTML = ''; // Limpa a tabela
    
    try {
        const response = await fetch(API_BASE_URL);
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const usuarios = await response.json();
        
        if (usuarios.length === 0) {
            loadingMessage.textContent = 'Nenhum usuário cadastrado.';
        } else {
            loadingMessage.textContent = '';
            usuarios.forEach(createTableRow);
        }
        
    } catch (error) {
        console.error('Erro ao buscar usuários:', error);
        loadingMessage.textContent = 'Erro ao carregar dados. Verifique o console.';
    }
};

// CREATE/UPDATE: Salvar ou Atualizar
const salvarUsuario = async (event) => {
    event.preventDefault(); // Impede o recarregamento da página

    const id = usuarioIdInput.value;
    const nome = nomeInput.value;
    const email = emailInput.value;

    const method = id ? 'PUT' : 'POST';
    const url = id ? `${API_BASE_URL}/${id}` : API_BASE_URL;

    const data = { nome, email };

    try {
        const response = await fetch(url, {
            method: method,
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
        });

        if (!response.ok) {
            // Se for um erro de validação do @Valid, o Spring pode retornar 400
            throw new Error(`Falha ao salvar usuário. Status: ${response.status}`);
        }
        
        // A API retorna o objeto salvo/atualizado
        await response.json();
        
        clearForm(); // Limpa o formulário após sucesso
        listarUsuarios(); // Recarrega a tabela
        
    } catch (error) {
        console.error('Erro ao salvar usuário:', error);
        alert(`Erro ao salvar usuário: ${error.message}`);
    }
};

// DELETE: Excluir
const excluirUsuario = async (id) => {
    if (!confirm(`Tem certeza que deseja excluir o usuário com ID ${id}?`)) {
        return;
    }

    try {
        const response = await fetch(`${API_BASE_URL}/${id}`, {
            method: 'DELETE',
        });

        if (!response.ok) {
            throw new Error(`Falha ao excluir. Status: ${response.status}`);
        }

        listarUsuarios(); // Recarrega a tabela
    } catch (error) {
        console.error('Erro ao excluir usuário:', error);
        alert(`Erro ao excluir usuário: ${error.message}`);
    }
};

// UPDATE (Parte 1): Preencher o formulário para edição
const prepararEdicao = async (id) => {
    try {
        const response = await fetch(`${API_BASE_URL}/${id}`);
        if (!response.ok) {
            throw new Error('Usuário não encontrado.');
        }
        const usuario = await response.json();
        
        // Preenche o formulário com os dados do usuário
        usuarioIdInput.value = usuario.id;
        nomeInput.value = usuario.nome;
        emailInput.value = usuario.email;
        
        submitButton.textContent = 'Salvar Edição';
        cancelButton.style.display = 'inline-block';
        
    } catch (error) {
        console.error('Erro ao buscar usuário para edição:', error);
        alert(error.message);
    }
};

// --- Listeners de Eventos ---

// 1. Carregar lista quando a página carrega
// ESTA LINHA FOI MOVIDA PARA O FINAL, ONDE A FUNÇÃO listarUsuarios JÁ FOI DEFINIDA
window.onload = listarUsuarios;

// 2. Submeter formulário (Criação ou Edição)
usuarioForm.addEventListener('submit', salvarUsuario);

// 3. Botão Cancelar Edição
cancelButton.addEventListener('click', clearForm);

// 4. Delegação de Eventos para os botões da tabela (Editar e Excluir)
usuariosTableBody.addEventListener('click', (event) => {
    const target = event.target;
    const id = target.getAttribute('data-id');

    if (!id) return;

    if (target.classList.contains('edit-btn')) {
        prepararEdicao(id);
    } else if (target.classList.contains('delete-btn')) {
        excluirUsuario(id);
    }
});