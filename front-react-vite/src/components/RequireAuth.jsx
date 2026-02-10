import { redirect } from 'react-router-dom';

function isTokenValid(token) {
    if (!token) return false;

    try {
        // Check JWT format (3 parts separated by dots)
        const parts = token.split('.');
        if (parts.length !== 3) return false;

        // Decode payload (second part)
        const payload = JSON.parse(atob(parts[1]));

        // Check expiration (exp is in seconds, Date.now() is in milliseconds)
        if (payload.exp && payload.exp * 1000 < Date.now()) {
            return false;
        }

        return true;
    } catch (error) {
        return false;
    }
}

export async function requireAuth() {
    const token = sessionStorage.getItem('token');

    if (!isTokenValid(token)) {
        sessionStorage.removeItem('token');
        return redirect('/login');
    }

    return null;
}

// Note: This loader function still uses sessionStorage directly
// because React Router loaders can't access React Context.
// For component-level auth checks, use the useAuth hook instead.
