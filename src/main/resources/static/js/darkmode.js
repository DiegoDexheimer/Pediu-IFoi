function toggleDarkMode() {
  const isDark = document.documentElement.classList.toggle('dark');
  localStorage.setItem('darkMode', isDark);
  updateDarkModeIcon(isDark);
}

function updateDarkModeIcon(isDark) {
  const icon = document.getElementById('darkModeIcon');
  if (!icon) return;

  if (isDark) {
    icon.innerHTML = '<svg class="h-6 w-6" fill="currentColor" viewBox="0 0 24 24"><path d="M6.995 12c0 2.761 2.246 5.007 5.005 5.007s5.005-2.246 5.005-5.007c0-2.761-2.246-5.007-5.005-5.007s-5.005 2.246-5.005 5.007zm13.705-1h-2.021a7.978 7.978 0 0 0-1.357-3.274l1.431-1.431a1 1 0 1 0-1.414-1.414l-1.431 1.431a7.978 7.978 0 0 0-3.274-1.357V2.3a1 1 0 1 0-2 0v2.021a7.978 7.978 0 0 0-3.274 1.357l-1.431-1.431a1 1 0 1 0-1.414 1.414l1.431 1.431a7.978 7.978 0 0 0-1.357 3.274H2.3a1 1 0 1 0 0 2h2.021a7.978 7.978 0 0 0 1.357 3.274l-1.431 1.431a1 1 0 1 0 1.414 1.414l1.431-1.431a7.978 7.978 0 0 0 3.274 1.357v2.021a1 1 0 1 0 2 0v-2.021a7.978 7.978 0 0 0 3.274-1.357l1.431 1.431a1 1 0 1 0 1.414-1.414l-1.431-1.431a7.978 7.978 0 0 0 1.357-3.274h2.021a1 1 0 1 0 0-2z"/></svg>';
  } else {
    icon.innerHTML = '<svg class="h-6 w-6" fill="currentColor" viewBox="0 0 24 24"><path d="M12 3a9 9 0 0 0 0 18c4.97 0 9-4.03 9-9 0-4.97-4.03-9-9-9zm0 16a7 7 0 0 1 0-14c.34 0 .67.02 1 .05A7.001 7.001 0 0 1 12 19z"/></svg>';
  }
}

window.addEventListener('DOMContentLoaded', () => {
  const isDark = localStorage.getItem('darkMode') === 'true';
  if (isDark) {
    document.documentElement.classList.add('dark');
  } else {
    document.documentElement.classList.remove('dark');
  }
  updateDarkModeIcon(isDark);
});
