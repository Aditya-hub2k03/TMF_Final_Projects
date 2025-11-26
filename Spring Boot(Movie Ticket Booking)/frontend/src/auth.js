export function saveAuth(authResponse) {
  if (authResponse.token) {
    localStorage.setItem('jwt', authResponse.token)
  }
  localStorage.setItem('user', JSON.stringify(authResponse))
}

export function getCurrentUser() {
  const raw = localStorage.getItem('user')
  if (!raw) return null
  try {
    return JSON.parse(raw)
  } catch {
    return null
  }
}

export function logout() {
  localStorage.removeItem('jwt')
  localStorage.removeItem('user')
}