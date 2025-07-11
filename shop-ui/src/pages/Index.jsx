
import React from 'react';
import { Routes, Route } from 'react-router-dom';
import { CartProvider } from '../context/CartContext';
import Header from '../components/Header';
import HomePage from '../components/HomePage';
import BookDetails from '../components/BookDetails';
import CartPage from '../components/CartPage';
import './Index.css';

const Index = () => {
  return (
    <CartProvider>
      <div className="app">
        <Header />
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/book/:id" element={<BookDetails />} />
          <Route path="/cart" element={<CartPage />} />
        </Routes>
      </div>
    </CartProvider>
  );
};

export default Index;
