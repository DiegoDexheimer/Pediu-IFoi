document.addEventListener('DOMContentLoaded', function () {
  // Verifica se Bootstrap está disponível
  if (typeof bootstrap === 'undefined') {
    console.error('Bootstrap JS não está carregado!');
    return;
  }
  const modalEl = document.getElementById('exampleModal');
  if (modalEl) {
    // Seleciona todos os botões que abrem o modal
    const modalBtns = document.querySelectorAll('button[data-bs-target="#exampleModal"]');
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
});

