
import React from 'react';
import { useParams, Link } from 'react-router-dom';
import { books } from '../data/books';
import { useCart } from '../context/CartContext';
import './BookDetails.css';

const BookDetails = () => {
  const { id } = useParams();
  const { addToCart } = useCart();
  
  const book = books.find(b => b.id === parseInt(id));

  if (!book) {
    return (
      <div className="container">
        <div className="book-not-found">
          <h2>Book not found</h2>
          <Link to="/" className="back-link">← Back to Home</Link>
        </div>
      </div>
    );
  }

  const handleAddToCart = () => {
    addToCart(book);
  };

  return (
    <div className="book-details">
      <div className="container">
        <Link to="/" className="back-link">← Back to Books</Link>
        
        <div className="book-details-content">
          <div className="book-image-large">
            <img src={book.image} alt={book.title} />
          </div>
          
          <div className="book-details-info">
            <h1 className="book-title-large">{book.title}</h1>
            <p className="book-author-large">by {book.author}</p>
            
            <div className="book-meta">
              <span className="book-category-large">{book.category}</span>
              
              <div className="book-rating-large">
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
            </div>
            
            <p className="book-description">{book.description}</p>
            
            <div className="book-actions">
              <span className="book-price-large">${book.price.toFixed(2)}</span>
              <button 
                className="add-to-cart-btn-large"
                onClick={handleAddToCart}
              >
                Add to Cart
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default BookDetails;
