
import React, { useState, useMemo } from 'react';
import { books, categories } from '../data/books';
import BookCard from './BookCard';
import SearchFilter from './SearchFilter';
import './HomePage.css';

const HomePage = () => {
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedCategory, setSelectedCategory] = useState('All');
  const [selectedAuthor, setSelectedAuthor] = useState('All');

  const authors = useMemo(() => {
    const uniqueAuthors = [...new Set(books.map(book => book.author))];
    return ['All', ...uniqueAuthors];
  }, []);

  const filteredBooks = useMemo(() => {
    return books.filter(book => {
      const matchesSearch = book.title.toLowerCase().includes(searchTerm.toLowerCase()) ||
                           book.author.toLowerCase().includes(searchTerm.toLowerCase());
      const matchesCategory = selectedCategory === 'All' || book.category === selectedCategory;
      const matchesAuthor = selectedAuthor === 'All' || book.author === selectedAuthor;
      
      return matchesSearch && matchesCategory && matchesAuthor;
    });
  }, [searchTerm, selectedCategory, selectedAuthor]);

  const featuredBooks = books.filter(book => book.featured);

  return (
    <div className="home-page">
      {/* Hero Section */}
      <section className="hero">
        <div className="container">
          <div className="hero-content">
            <h1 className="hero-title">Discover Your Next Great Read</h1>
            <p className="hero-subtitle">
              Explore our curated collection of books across all genres. 
              From timeless classics to modern bestsellers.
            </p>
            <a href="#featured" className="cta-button">
              Shop Now
            </a>
          </div>
          <div className="hero-image">
            <img 
              src="https://images.unsplash.com/photo-1481627834876-b7833e8f5570?w=600&h=400&fit=crop" 
              alt="Stack of books"
            />
          </div>
        </div>
      </section>

      {/* Featured Books */}
      <section id="featured" className="featured-section">
        <div className="container">
          <h2 className="section-title">Featured Books</h2>
          <div className="books-grid">
            {featuredBooks.map(book => (
              <BookCard key={book.id} book={book} />
            ))}
          </div>
        </div>
      </section>

      {/* All Books with Search and Filter */}
      <section className="all-books-section">
        <div className="container">
          <h2 className="section-title">All Books</h2>
          
          <SearchFilter
            searchTerm={searchTerm}
            setSearchTerm={setSearchTerm}
            selectedCategory={selectedCategory}
            setSelectedCategory={setSelectedCategory}
            selectedAuthor={selectedAuthor}
            setSelectedAuthor={setSelectedAuthor}
            categories={categories}
            authors={authors}
          />

          <div className="books-grid">
            {filteredBooks.map(book => (
              <BookCard key={book.id} book={book} />
            ))}
          </div>

          {filteredBooks.length === 0 && (
            <div className="no-results">
              <p>No books found matching your criteria.</p>
            </div>
          )}
        </div>
      </section>
    </div>
  );
};

export default HomePage;
