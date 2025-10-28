document.addEventListener('DOMContentLoaded', function () {
  window.abrirModalCancelar = function (btn) {
    const pedidoId = btn.getAttribute('data-pedido-id');
    const modal = document.getElementById('modalCancelarPedido');
    modal.classList.remove('hidden');
    modal.setAttribute('data-pedido-id', pedidoId);
    modal._btnCancelarRef = btn;
  };

  document.getElementById('btnFecharModalCancelar').onclick = function () {
    document.getElementById('modalCancelarPedido').classList.add('hidden');
  };

  document.getElementById('btnConfirmarCancelar').onclick = function () {
    const modal = document.getElementById('modalCancelarPedido');
    const pedidoId = modal.getAttribute('data-pedido-id');
    const btn = modal._btnCancelarRef;
    if (btn) btn.disabled = true;
    fetch('/pedidos/cancelar', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(pedidoId)
    })
      .then(response => {
        if (response.ok) {
          const statusSpan = btn.closest('li').querySelector('.status-pedido');
          if (statusSpan) {
            statusSpan.textContent = 'Cancelado';
            statusSpan.className = 'px-4 py-1 rounded-lg font-bold bg-red-100 text-red-800 status-pedido';
          }
          btn.remove();
          modal.classList.add('hidden');
        } else {
          alert('Erro ao cancelar o pedido.');
          if (btn) btn.disabled = false;
        }
      })
      .catch(() => {
        alert('Erro ao cancelar o pedido.');
        if (btn) btn.disabled = false;
      });
  };
});
