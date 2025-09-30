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

  // Modal de detalhes do pedido
  const pedidoModalEl = document.getElementById('pedidoModal');
  if (pedidoModalEl) {
    document.querySelectorAll('.dashboard-order').forEach(function (orderDiv) {
      orderDiv.addEventListener('click', function () {
        const pedidoId = orderDiv.getAttribute('data-pedido-id');
        // Busca os itens do pedido via AJAX (exemplo usando fetch)
        fetch(`/cantina/pedido/${pedidoId}/itens`)
          .then(response => response.json())
          .then(data => {
            let total = 0;
            let html = `<div style="max-width:400px;">
              <div class="pedido-modal-header">
                <span style="font-size:1.15em;font-weight:bold;">Pedido #${pedidoId}</span>
              </div>
            `;
            if (data && data.length > 0) {
              data.forEach(item => {
                total += item.quantidade * item.produto.preco;
                let foto = item.produto.foto && item.produto.foto.trim() ? item.produto.foto : '/default.png';
                html += `
                  <div class="pedido-item-row">
                    <img src="${foto}" alt="${item.produto.nome}" class="pedido-item-img">
                    <div class="pedido-item-info">
                      <div class="pedido-item-top">
                        <strong class="pedido-item-nome">${item.produto.nome}</strong>
                        <span class="pedido-item-quantidade">${item.quantidade}x</span>
                      </div>
                      <div class="pedido-item-obs">${item.observacao || ''}</div>
                    </div>
                    <div class="pedido-item-preco">
                      <span>R$ ${item.produto.preco.toFixed(2)}</span>
                    </div>
                  </div>
                `;
              });
            } else {
              html += '<div>Nenhum produto neste pedido.</div>';
            }
            html += `<hr><div class="pedido-total-row"><span>Total</span><span>R$ ${total.toFixed(2)}</span></div></div>`;
            document.getElementById('pedidoModalBody').innerHTML = html;
            new bootstrap.Modal(pedidoModalEl).show();
          })
          .catch(() => {
            document.getElementById('pedidoModalBody').innerHTML = 'Erro ao carregar itens do pedido.';
            new bootstrap.Modal(pedidoModalEl).show();
          });
      });
    });

  // Integração dos botões de ação para mudança de status
  document.querySelectorAll('.dashboard-btn').forEach(function (btn) {
    btn.addEventListener('click', function (e) {
      e.stopPropagation();
      const orderDiv = btn.closest('.dashboard-order');
      const pedidoId = orderDiv.getAttribute('data-pedido-id');
      let novoStatus = null;
      if (btn.textContent.includes('Aceitar pedido')) {
        novoStatus = 'EM_ANDAMENTO';
      } else if (btn.textContent.includes('Avançar pedido')) {
        novoStatus = 'FINALIZADO';
      } else if (btn.textContent.includes('Finalizar entrega')) {
        novoStatus = 'ENVIADO';
      }
      if (novoStatus && pedidoId) {
        fetch(`/pedidos/${pedidoId}/status?status=${novoStatus}`, {
          method: 'PUT',
        })
          .then(response => {
            if (response.ok) {
              window.location.reload();
            } else {
              alert('Erro ao atualizar status do pedido.');
            }
          })
          .catch(() => {
            alert('Erro ao atualizar status do pedido.');
          });
      }
    });
  });
  }
});