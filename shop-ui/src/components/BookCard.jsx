
import React from 'react';
import { Link } from 'react-router-dom';
import { useCart } from '../context/CartContext';
import './BookCard.css';

const BookCard = ({ book }) => {
  const { addToCart } = useCart();

  const handleAddToCart = (e) => {
    e.preventDefault();
    e.stopPropagation();
    addToCart(book);
  };

  return (
    <div className="book-card">
      <Link to={`/book/${book.id}`} className="book-link">
        <div className="book-image">
          <img src={book.image} alt={book.title} />
          <div className="book-overlay">
            <span>View Details</span>
          </div>
        </div>
        
        <div className="book-info">
          <h3 className="book-title">{book.title}</h3>
          <p className="book-author">by {book.author}</p>
          <p className="book-category">{book.category}</p>
          
          <div className="book-rating">
            {[...Array(5)].map((_, i) => (
              <span 
                key={i} 
                className={`star ${i < Math.floor(book.rating) ? 'filled' : ''}`}
              >
                ★
              </span>
            ))}
            <span className="rating-value">({book.rating})</span>
          </div>
          
          <div className="book-footer">
            <span className="book-price">${book.price.toFixed(2)}</span>
            <button 
              className="add-to-cart-btn"
              onClick={handleAddToCart}
            >
              Add to Cart
            </button>
          </div>
        </div>
      </Link>
    </div>
  );
};

export default BookCard;
