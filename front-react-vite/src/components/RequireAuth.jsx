import { redirect } from 'react-router-dom';

export async function requireAuth() {
    const token = sessionStorage.getItem('token');
    if (!token) {
        return redirect('/login');
    }
    return null;
}
