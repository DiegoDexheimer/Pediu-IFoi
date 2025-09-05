document.addEventListener('DOMContentLoaded', function () {
  // Verifica se Bootstrap está disponível
  if (typeof bootstrap === 'undefined') {
    console.error('Bootstrap JS não está carregado!');
    return;
  }
  const modalEl = document.getElementById('exampleModal');
  if (modalEl) {
    // Seleciona todos os botões que abrem o modal
    const modalBtns = document.querySelectorAll('button[data-bs-target="#editarModal"]');
    const modal = new bootstrap.Modal(modalEl);
    modalBtns.forEach(function (btn) {
      btn.addEventListener('click', function (e) {
        e.preventDefault();

        // Salva dados do produto no modal
        const produtoId = btn.getAttribute('data-bs-produto-id');
        const produtoNome = btn.getAttribute('data-bs-produto-nome');
        const produtoPreco = btn.getAttribute('data-bs-produto-preco');

        // Atualiza título e campos do modal
        const modalTitle = modalEl.querySelector('.modal-title');
        const recipientInput = modalEl.querySelector('#recipient-name');
        const priceInput = modalEl.querySelector('#recipient-price');
        const produtoIdInput = modalEl.querySelector('#produto-id');
        const formAtualizar = modalEl.querySelector('#formAtualizarProduto');

        if (modalTitle) modalTitle.textContent = `Editar produto #${produtoId}`;
        if (recipientInput) recipientInput.value = produtoNome || '';
        if (priceInput) priceInput.value = produtoPreco || '';
        if (produtoIdInput) produtoIdInput.value = produtoId || '';
        if (formAtualizar) formAtualizar.action = `/cantina/atualizar_produto/${produtoId}`;
        
        modal.show();
      });
    });
  }

  const editarModalEl = document.getElementById('editarModal');
  if (editarModalEl) {
    const editarBtns = document.querySelectorAll('button[data-bs-target="#editarModal"]');
    const editarModal = new bootstrap.Modal(editarModalEl);
    editarBtns.forEach(function (btn) {
      btn.addEventListener('click', function (e) {
        e.preventDefault();
        const produtoId = btn.getAttribute('data-bs-produto-id');
        const produtoNome = btn.getAttribute('data-bs-produto-nome');
        const produtoPreco = btn.getAttribute('data-bs-produto-preco');
        const modalTitle = editarModalEl.querySelector('.modal-title');
        const recipientInput = editarModalEl.querySelector('#produto-name');
        const priceInput = editarModalEl.querySelector('#produto-price');
        const produtoIdInput = editarModalEl.querySelector('#produto-id');
        const formAtualizar = editarModalEl.querySelector('#formAtualizarProduto');
        if (modalTitle) modalTitle.textContent = `Editar produto #${produtoId}`;
        if (recipientInput) recipientInput.value = produtoNome || '';
        if (priceInput) priceInput.value = produtoPreco || '';
        if (produtoIdInput) produtoIdInput.value = produtoId || '';
        if (formAtualizar) formAtualizar.action = `/cantina/atualizar_produto/${produtoId}`;
        editarModal.show();
      });
    });
  }

  // Modal Remover
  const removerModalEl = document.getElementById('removerModal');
  if (removerModalEl) {
    const removerBtns = document.querySelectorAll('button[data-bs-target="#removerModal"]');
    const removerModal = new bootstrap.Modal(removerModalEl);

    removerBtns.forEach(function (btn) {
      btn.addEventListener('click', function (e) {
        e.preventDefault();
        const produtoId = btn.getAttribute('data-bs-produto-id');
        const formRemover = removerModalEl.querySelector('#formRemoverProduto');
        if (formRemover) formRemover.action = `/cantina/remover_produto/${produtoId}`;
        removerModal.show();
      });
    });
  }
});